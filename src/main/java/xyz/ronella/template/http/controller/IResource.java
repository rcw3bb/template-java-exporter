package xyz.ronella.template.http.controller;

import xyz.ronella.template.http.wrapper.SimpleHttpExchange;

public interface IResource {
    boolean canProcess(SimpleHttpExchange simpleExchange);

    void process(SimpleHttpExchange simpleExchange);

    String getPathPattern();
}
