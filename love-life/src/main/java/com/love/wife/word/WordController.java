package com.love.wife.word;

import com.love.wife.service.WordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author chenlujia
 * @date 2020-08-11
 * @description word文档管理
 */
@Controller
@RequestMapping(value = "/word-manager")
public class WordController {

    @Resource
    private WordService tableService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${swagger.url}")
    private String swaggerUrl;

    @Value("${server.port}")
    private Integer port;


    @GetMapping(value = "/toIndex")
    public String index(Model model) {
        model.addAttribute("user", "陈路嘉");
        return "index";
    }

    /**
     * 将 swagger 文档转换成 html 文档，可通过在网页上右键另存为 xxx.doc 的方式转换为 word 文档
     *
     * @param model
     * @param url   需要转换成 word 文档的资源地址
     * @return
     */
    @Deprecated
    @GetMapping("/toWord")
    public String getWord(Model model, @RequestParam(value = "url", required = false) String url,
                          @RequestParam(value = "download", required = false, defaultValue = "1") Integer download) {
        url = StringUtils.defaultIfBlank(url, swaggerUrl);
        Map<String, Object> result = tableService.tableList(url);
        model.addAttribute("url", url);
        model.addAttribute("download", download);
        model.addAllAttributes(result);
        return "word";
    }

    /**
     * 将 swagger 文档一键下载为 doc 文档
     * 注意url地址
     *
     * @param url      需要转换成 word 文档的资源地址
     * @param response
     */
    @GetMapping("/downloadWord")
    public void word(@RequestParam(required = false) String url, HttpServletResponse response) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/love/word-manager/toWord?download=0&url=" + StringUtils.defaultIfBlank(url, swaggerUrl), String.class);
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("toWord.doc", "utf-8"));
            byte[] bytes = forEntity.getBody().getBytes();
            bos.write(bytes, 0, bytes.length);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转视频播放页面
     */
    @GetMapping(value = "/video")
    public String video() {
        return "video";
    }

    /**
     * 跳转音频播放页面
     */
    @GetMapping(value = "/audio")
    public String audio() {
        return "audio";
    }

    /**
     * 跳转pdf
     */
    @GetMapping(value = "/pdf")
    public String pdf() {
        return "pdf";
    }


    public void test() {

    }
}
