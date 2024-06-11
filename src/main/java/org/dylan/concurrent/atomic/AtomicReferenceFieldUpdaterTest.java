package org.dylan.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author Dylan
 * @version 2024/6/8
 */
public class AtomicReferenceFieldUpdaterTest {


    public static final AtomicReferenceFieldUpdater<Log, String> logMessageUpdater = AtomicReferenceFieldUpdater
            .newUpdater(Log.class, String.class, "logMessage");

    public static void main(String[] args) {

        final Log log = new Log("a");
        if (logMessageUpdater.compareAndSet(log, "a", "b")) {
            System.out.println("原子更新成功");
        }
        System.out.println(log.logMessage);
    }

    public static class UpdateMessageUpdaterTask implements Runnable {

        public final AtomicReferenceFieldUpdater<Log, String> logMessageUpdater;

        public UpdateMessageUpdaterTask(AtomicReferenceFieldUpdater<Log, String> logMessageUpdater) {
            this.logMessageUpdater = logMessageUpdater;
        }

        @Override
        public void run() {
        }
    }

    public static class Log {
        volatile String logMessage = "";

        public Log(String logMessage) {
            this.logMessage = logMessage;
        }
    }
}
