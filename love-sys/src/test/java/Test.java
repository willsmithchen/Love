import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

import java.io.File;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/11/7 21:08
 * @description
 */

public class Test {
    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("D:/Project/Love/love-sys/src/main/java/com/love/sys/controller");
        config.setProjectName("用户管理配置");
        config.setApiVersion("1.0-version");
        config.setDocsPath("D:/Project/Love/love-sys/src/main/resources/swagger/");
        config.setAutoGenerate(true);
        Docs.buildHtmlDocs(config);
    }
}
