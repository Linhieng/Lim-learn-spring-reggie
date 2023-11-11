package cn.oonoo.reggie.common;

/**
 * 基于 ThreadLocal 封装工具类，用户保存和获取当前登录用户id
 * ThreadLocal并不是一个 Thread，而是 Thread 的局部变量。
 * 当使用 ThreadLocal 维护变量时，ThreadLocal 为每个使用该变量的线程提供独立的变量副本，
 * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
 * ThreadLocal 为每个线程提供单独一份存储空间，具有线程隔离的效果，
 * 只有在线程内才能获取到对应的值，线程外则不能访问。
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
