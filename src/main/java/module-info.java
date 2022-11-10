open module xyz.ronella.template.http {
    requires static lombok;

    requires com.fasterxml.jackson.databind;
    requires jdk.httpserver;

    requires xyz.ronella.casual.trivial;
    requires xyz.ronella.logging.logger.plus;

    requires com.google.guice;
    requires org.slf4j;
    requires org.apache.logging.log4j;

}