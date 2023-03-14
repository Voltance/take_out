package com.itheima.reggie.common;

public class BaseContext {
    private static final ThreadLocal<Long> THREAD_LOCAL_USERID = new ThreadLocal<>();

    public static Long getCurrent() {
        return THREAD_LOCAL_USERID.get();
    }

    public static void setCurrent(Long id) {
        THREAD_LOCAL_USERID.set(id);
    }
}
