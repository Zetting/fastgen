package com.fastgen.core.util;

import cn.hutool.core.io.FileUtil;
import com.fastgen.core.base.Contants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * freemarker相关工具
 *
 * @author: zet
 * @date:2019/9/20
 */
@Slf4j
@Component
public class FreemarkerUtil {

    private Configuration configuration;

    public void configuration(String templatePath) throws IOException {
        configuration = new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding(Contants.CHARSET_CODE);
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
    }


    /**
     * 转换
     *
     * @param freemarkerStr frl模板内容
     * @param dataModel     模板参数
     * @return 转换后数据
     * @throws Exception
     */
    public String parse(String freemarkerStr, Object dataModel) throws Exception {
        StringWriter writer = null;
        try {
            Template template = new Template((String) null, new StringReader(freemarkerStr), null);
            writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new Exception("转换异常");
        }
    }

    /**
     * 转换模板写入文件
     *
     * @param templateFilePath
     * @param writerFilePath
     * @param properties
     * @throws IOException
     */
    public void writer(String templateFilePath, String writerFilePath, Properties properties) {
        try {
            configuration(FileUtil.getParent(templateFilePath, 1));
            Template template = configuration.getTemplate(FileUtil.getName(templateFilePath));
            File file = new File(writerFilePath);
            if (!file.exists()) {
                new File(writerFilePath).getParentFile().mkdirs();
            }
            Writer out = new FileWriter(file);
            template.process(properties, out);
            out.close();
        } catch (TemplateException | IOException e) {
            log.error("freeamarker 异常", e);
        }
    }

}
