package com.love.wife.model.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/9 23:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Image {
    private byte[] imageByte;
}
