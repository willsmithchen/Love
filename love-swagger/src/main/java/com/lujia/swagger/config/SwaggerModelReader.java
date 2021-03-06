package com.lujia.swagger.config;

import com.lujia.swagger.annotation.ApiProperties;
import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModelProperty;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zenghao
 * @date 2019-12-10
 */
@Slf4j
@Order
@Component
public class SwaggerModelReader implements ParameterBuilderPlugin {

    @Resource
    private TypeResolver typeResolver;

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
        com.google.common.base.Optional<ApiProperties> optional = methodParameter.findAnnotation(ApiProperties.class);
        if (optional.isPresent()) {
            Class originClass = parameterContext.resolvedMethodParameter().getParameterType().getErasedType();
            ApiProperties apiProperties = optional.get();
            String name = apiProperties.name();
            String[] properties = null;

            boolean ignoreType = apiProperties.exclude().length > 0;
            if (ignoreType) {
                properties = apiProperties.exclude();
            } else {
                properties = apiProperties.include();
            }
            try {
                //???documentContext???Models???????????????????????????Class
                parameterContext.getDocumentationContext()
                        .getAdditionalModels()
                        .add(typeResolver.resolve(createRefModel(properties, name, originClass, ignoreType)));
            } catch (Exception e) {
                log.error("?????????????????????", e);
            }

            parameterContext.parameterBuilder()  //??????model?????????ModelRef????????????????????????class
                    .parameterType("body")
                    .modelRef(new ModelRef(name))
                    .name(name);
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }

    /**
     * ?????? properties ???????????????????????????Swagger?????????javaBeen
     */
    private Class createRefModel(String[] properties, String name, Class origin, boolean ignoreType) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(origin.getPackage().getName() + "." + name);
        try {
            Field[] fields = origin.getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            List<String> dealProperties = Arrays.asList(properties);
            //???????????????????????????
            List<Field> dealFields = fieldList.stream().filter(f -> !Modifier.isFinal(f.getModifiers()))
                    .filter(f -> ignoreType == (!(dealProperties.contains(f.getName())))
                    ).collect(Collectors.toList());
            createCtFields(dealFields, ctClass);
            return ctClass.toClass();

        } catch (Exception e) {
            log.error("??????model?????????", e);
            return null;
        }
    }

    public void createCtFields(List<Field> dealFileds, CtClass ctClass) throws CannotCompileException, NotFoundException {
        for (Field field : dealFileds) {
            CtField ctField = new CtField(ClassPool.getDefault().get(field.getType().getName()), field.getName(), ctClass);
            ctField.setModifiers(Modifier.PUBLIC);
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            String apiModelPropertyValue = java.util.Optional.ofNullable(annotation).map(ApiModelProperty::value).orElse("");
            //??????model????????????
            if (!StringUtils.isEmpty(apiModelPropertyValue)) {
                ConstPool constPool = ctClass.getClassFile().getConstPool();
                AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
                Annotation ann = new Annotation(ApiModelProperty.class.getName(), constPool);
                ann.addMemberValue("value", new StringMemberValue(apiModelPropertyValue, constPool));
                attr.addAnnotation(ann);
                ctField.getFieldInfo().addAttribute(attr);
            }
            ctClass.addField(ctField);
        }
    }


}
