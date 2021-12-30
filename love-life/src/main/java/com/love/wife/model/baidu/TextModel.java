package com.love.wife.model.baidu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/11 13:55
 * 百度文字转音频的文本参数模式
 */
@ApiModel(value = "文本参数模式")
@Data
@ToString
public class TextModel {
    /**
     * 要转换的文字内容
     */
    @ApiModelProperty(value = "要转换的文字内容", position = 1)
    private String text;
    /**
     * 发音人选择，基础音库。0为小美，1为小宇，3为度逍遥，4为度丫丫
     * 精品音库，5为度小娇，103为度米朵，106为度文博，110为度小童，111为度小萌，默认为度小美
     */
    @ApiModelProperty(value = "发音人选择，基础音库。0为小美，1为小宇，3为度逍遥，4为度丫丫\n" +
            "精品音库，5为度小娇，103为度米朵，106为度文博，110为度小童，111为度小萌，默认为度小美",position = 2)
    private Integer per;
    /**
     * 语速,取值0-15，默认为5中语速
     */
    @ApiModelProperty(value = "语速,取值0-15，默认为5中语速",position = 3)
    private Integer spd;
    /**
     * 音调，取值0-15，默认为5中语调
     */
    @ApiModelProperty(value = "音调，取值0-15，默认为5中语调",position = 4)
    private Integer pit;
    /**
     * 音量，取值0-9，默认为5为中音量
     */
    @ApiModelProperty(value = "音量，取值0-9，默认为5为中音量",position = 5)
    private Integer vol;
    /**
     * 下载的文件格式，3:mp3,4:pcm-16k,5:pcm-8k,6:.wav
     */
    @ApiModelProperty(value = "下载的文件格式，3:mp3,4:pcm-16k,5:pcm-8k,6:.wav",position = 6)
    private Integer aue;
}
