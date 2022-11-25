package xyz.ronella.template.exporter.wrapper;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.LoggerFactory;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.template.exporter.config.AppConfig;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * The only class that knows about the freemarker.
 * @author Ron Webb
 * @since 1.0.0
 */
public final class SimpleTemplate {

    private static final Configuration CONFIG = new Configuration(Configuration.VERSION_2_3_29);
    private static final LoggerPlus LOGGER_PLUS = new LoggerPlus(LoggerFactory.getLogger(SimpleTemplate.class));

    static {
        try {
            CONFIG.setDirectoryForTemplateLoading(AppConfig.INSTANCE.getConfDir("/templates").orElseThrow());
        } catch (IOException ioe) {
            LOGGER_PLUS.error(LOGGER_PLUS.getStackTraceAsString(ioe));
            throw new RuntimeException(ioe);
        }
        CONFIG.setDefaultEncoding(StandardCharsets.UTF_8.name());
        CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIG.setLogTemplateExceptions(false);
        CONFIG.setWrapUncheckedExceptions(true);
        CONFIG.setFallbackOnNullLoopVariable(false);
    }

    private SimpleTemplate() {}

    /**
     * The method for bounding the template and model and write to an output.
     * @param templateFile The name of the template file.
     * @param dataModel The model to fill in the tokens in the template.
     * @param out The output to write the result of bounding the template and the model.
     * @throws IOException The exception that must be handled.
     */
    public static void processTemplate(final String templateFile, final Object dataModel, final Writer out) throws IOException {
        try(var mLOG = LOGGER_PLUS.groupLog("static void processTemplate(String,Object,Writer)")) {
            try {
                var template = CONFIG.getTemplate(templateFile);
                template.process(dataModel, out);
            } catch (TemplateException te) {
                mLOG.error(LOGGER_PLUS.getStackTraceAsString(te));
                throw new RuntimeException(te);
            }
        }
    }

    /**
     * The method for bounding the template and model to produce a string output.
     * @param templateFile The name of the template file.
     * @param dataModel The model to fill in the tokens in the template.
     * @return The text result of bounding the template and the model.
     * @throws IOException The exception the must be handled.
     */
    public static String processTemplate(final String templateFile, final Object dataModel) throws IOException {
        try(final var out = new StringWriter()) {
            processTemplate(templateFile, dataModel, out);
            return out.toString();
        }
    }
}
