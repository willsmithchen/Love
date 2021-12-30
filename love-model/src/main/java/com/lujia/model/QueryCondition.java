package com.lujia.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 动态查询条件 <see>QueryField</see>
 * @author zenghao
 */
@Data
@Accessors(chain = true)
public class QueryCondition {

    private Operator operator;
    private String filedName;
    private boolean queryEmpty;
    private boolean ignore;

    public static QueryCondition defaultCondition(){
        QueryCondition condition = new QueryCondition();
        condition.setOperator(Operator.EQ).setQueryEmpty(false).setIgnore(false);
        return condition;
    }

    public static QueryCondition of(QueryField queryField){
        QueryCondition condition = new QueryCondition();
        condition.setOperator(queryField.operator()).setFiledName(queryField.field())
                .setQueryEmpty(queryField.queryEmpty()).setIgnore(queryField.ignore());
        return condition;
    }
}
