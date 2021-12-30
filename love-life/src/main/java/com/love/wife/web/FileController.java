package com.love.wife.web;

import com.love.wife.model.baidu.TextModel;
import com.love.wife.service.AudioService;
import com.lujia.model.Outcome;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/26 23:20
 */

@RestController
@RequestMapping(value = "/file-manager")
@Api(tags = "文件管理")
public class FileController {
    @Resource
    private AudioService audioService;

    @ApiOperation(value = "上传图片")
    @PostMapping(value = "/upload-img")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", dataTypeClass = Long.class)
    public Outcome uploadImage(Long id, @ApiParam(value = "上传文件") MultipartFile file) {
        Boolean result = audioService.uploadImage(id, file);
        if (result) {
            return Outcome.success();
        }
        return Outcome.failure();
    }

    @ApiOperation(value = "预览图片")
    @GetMapping(value = "/view-image/{id}", produces = {"image/jpeg"})
    public void viewImage(@PathVariable Long id, HttpServletResponse response) {
        audioService.viewImage(id, response);
    }

    @SneakyThrows
    @ApiOperation(value = "文字转音频")
    @PostMapping(value = "/to-audio")
    public Outcome textToAudio(@ApiParam(value = "转换参数") TextModel textModel) {
        Boolean result = audioService.textToAudio(textModel);
        if (result) {
            return Outcome.success().setMessage("转换成功");
        }
        return Outcome.failure();
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/getCode", produces = "image/jpeg")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
        audioService.getCode(request, response);
    }

    @ApiOperation(value = "视频预览1")
    @GetMapping(value = "/getVideo")
    public void getVideo(HttpServletResponse response) {
        audioService.getVideo(response);
    }

    @ApiOperation(value = "视频预览2")
    @GetMapping(value = "/getPreview")
    public void getPreview(HttpServletResponse response) {
        audioService.getPreview(response);
    }

    /**
     * * @param bucketName 存储桶名称
     * * @param objectName 存储桶里的对象名称
     *
     * @param response
     */
    @ApiOperation(value = "视频预览3")
    @GetMapping(value = "/previewVideo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName", value = "存储桶名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "objectName", value = "存储桶里的对象名称", dataTypeClass = String.class, paramType = "query")
    })
    public void previewVideo(String bucketName, String objectName, HttpServletResponse response) {
        audioService.previewVideo(bucketName, objectName, response);
    }

    @ApiOperation(value = "音频预览")
    @GetMapping(value = "/previewAudio")
    public void previewAudio(HttpServletResponse response) {
        audioService.previewAudio(response);
    }

    @ApiOperation(value = "预览PDF")
    @GetMapping(value = "/preview-pdf", produces = "application/pdf")
    public void previewPdf(HttpServletResponse response) {
        audioService.previewPdf(response);
    }
}
