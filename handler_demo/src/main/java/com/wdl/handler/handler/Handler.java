package com.wdl.handler.handler;

/**
 *
 * @param <T>
 */
public interface Handler<T> {
    /***
     * 是否执行处理器
     * @param t
     */
    void isEnable(T t);

    /**
     * 执行处理器
     *
     * @param t
     */
    void handle(T t);
}
