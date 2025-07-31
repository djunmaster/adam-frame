package com.pilot.core;

import com.pilot.tools.lists.CollectionUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 高性能、高扩展性的 Word 导出服务。
 * 采用 Fluent API 和多目标导出策略，实现核心逻辑与输出目标的解耦。
 *
 * @author Java 高质量解决方案
 */
@Service
public class WordExportService {

    private static final Logger log = LoggerFactory.getLogger(WordExportService.class);

    private static final String WORD_TEMPLATE_PATH_PREFIX = "word/";
    private static final String WORD_MIME_TYPE = "application/msword";
    private static final String FILE_EXTENSION = ".doc";

    private final Configuration freemarkerConfig;

    @Autowired
    public WordExportService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    /**
     * 开始一个 Word 导出流程，指定模板名称。
     * 这是 Fluent API 的入口点。
     *
     * @param templateName 模板文件名 (例如: "user_info.ftl")
     * @return 返回一个 WordExporter 实例，用于后续链式调用。
     */
    public WordExporter withTemplate(String templateName) {
        Objects.requireNonNull(templateName, "Template name cannot be null.");
        return new WordExporter(templateName);
    }

    /**
     * WordExporter 是一个内部辅助类，实现了 Fluent API。
     * 它负责管理单次导出的上下文（模板、数据）并执行到不同目标的导出操作。
     */
    public class WordExporter {
        private final String templateName;
        private Map<String, Object> dataMap = Collections.emptyMap();

        private WordExporter(String templateName) {
            this.templateName = templateName;
        }

        /**
         * 设置模板所需的数据模型。
         *
         * @param dataMap 包含模板所需数据的 Map.
         * @return 当前 WordExporter 实例，用于链式调用。
         */
        public WordExporter withData(Map<String, Object> dataMap) {
            this.dataMap = CollectionUtil.isNotEmpty(dataMap) ? dataMap : Collections.emptyMap();
            return this;
        }

        /**
         * 【核心方法】生成 Word 文档的字节数组。
         * 这是所有导出操作的基础，实现了核心渲染逻辑与输出的分离。
         *
         * @return 包含 Word 文档内容的字节数组。
         * @throws RuntimeException 如果模板处理失败。
         */
        public byte[] toBytes() {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 Writer out = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

                Template template = freemarkerConfig.getTemplate(WORD_TEMPLATE_PATH_PREFIX + this.templateName);
                template.process(this.dataMap, out);
                out.flush();
                return baos.toByteArray();

            } catch (TemplateException e) {
                log.error("FreeMarker 模板处理失败: {}", this.templateName, e);
                throw new RuntimeException("模板渲染失败", e);
            } catch (IOException e) {
                log.error("内存流写入时发生 I/O 错误", e);
                throw new RuntimeException("生成文件字节流失败", e);
            }
        }

        /**
         * 【输出策略1】将生成的 Word 文档直接写入 HttpServletResponse，用于 Web 下载。
         *
         * @param response      HttpServletResponse 对象。
         * @param documentTitle 下载时显示的文件名（不含扩展名）。
         */
        public void toResponse(HttpServletResponse response, String documentTitle) {
            byte[] bytes = toBytes();
            setupResponseHeaders(response, documentTitle, bytes.length);
            try (OutputStream os = response.getOutputStream()) {
                os.write(bytes);
                os.flush();
                log.info("成功导出 Word 文档 '{}' 到 HttpServletResponse", documentTitle);
            } catch (IOException e) {
                log.error("写入响应流时发生 I/O 错误: {}", documentTitle, e);
            }
        }

        /**
         * 【输出策略2】将生成的 Word 文档写入任何 OutputStream。
         *
         * @param os 目标输出流。
         * @throws IOException 如果写入流失败。
         */
        public void toOutputStream(OutputStream os) throws IOException {
            byte[] bytes = toBytes();
            os.write(bytes);
            log.info("成功将 Word 文档写入指定的 OutputStream");
        }
    }

    private void setupResponseHeaders(HttpServletResponse response, String documentTitle, int contentLength) {
        try {
            String encodedFileName = URLEncoder.encode(documentTitle + FILE_EXTENSION, StandardCharsets.UTF_8.name())
                    .replaceAll("\\+", "%20");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(WORD_MIME_TYPE);
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
            response.setContentLength(contentLength);
        } catch (Exception e) {
            log.error("设置响应头失败", e);
        }
    }
}