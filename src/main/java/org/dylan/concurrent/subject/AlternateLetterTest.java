package org.dylan.concurrent.subject;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 三个线程交替打印 a, b, c
 *
 * @author Dylan
 * @version 2024/6/8
 */
public class AlternateLetterTest {


    public static void main(String[] args) {

        final AlternateLetterContext alternateLetterContext = new AlternateLetterContext();
        final AtomicReferenceFieldUpdater<AlternateLetterContext, String> letterUpdater = AtomicReferenceFieldUpdater.newUpdater(AlternateLetterContext.class, String.class, "letter");

        new Thread(new Task(alternateLetterContext, letterUpdater, "a", "b")).start();
        new Thread(new Task(alternateLetterContext, letterUpdater, "b", "c")).start();
        new Thread(new Task(alternateLetterContext, letterUpdater, "c", "a")).start();
    }

    // 记录上下文
    public static final class AlternateLetterContext {
        public volatile String letter = "a";
    }

    // 更新任务
    public static class Task implements Runnable {

        private final AlternateLetterContext context;
        private final AtomicReferenceFieldUpdater<AlternateLetterContext, String> letterUpdater;
        private final String from;
        private final String to;

        public Task(AlternateLetterContext context, AtomicReferenceFieldUpdater<AlternateLetterContext, String> letterUpdater, String from, String to) {
            this.context = context;
            this.letterUpdater = letterUpdater;
            this.from = from;
            this.to = to;
        }


        @Override
        public void run() {
            while (true) {
                synchronized (AlternateLetterTest.class) {
                    if (letterUpdater.compareAndSet(context, from, to)) {
                        System.out.println(Thread.currentThread().getName() + "--" + from);
                    }
                }
            }
        }
    }
}
