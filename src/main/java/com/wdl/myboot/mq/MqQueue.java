package com.wdl.myboot.mq;

import com.wdl.myboot.mq.entry.MqMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟消息队列
 *
 * @author wdl
 */
@Slf4j
public class MqQueue {
    /**
     * 锁，用于队列新增和删除时保持线程安全
     */
    private static final transient ReentrantLock lock = new ReentrantLock();
    /**
     * 队列大小
     */
    public static final int MAXSIZE = 1000000;
    private static Queue<MqMessage> queue = new LinkedList<>();
    /**
     * 添加元素
     */
    public static boolean addQueue(MqMessage mqMessage) {
        lock.lock();
        try {
            queue.offer(mqMessage);
            log.info("===========addQuequ success");
        } finally {
            lock.unlock();
        }
        return true;
    }
    /**
     * 取出元素并且删除
     */
    public static MqMessage pollQueue() {
        return queue.poll();
    }
}
