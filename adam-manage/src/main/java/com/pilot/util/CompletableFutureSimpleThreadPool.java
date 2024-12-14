package com.pilot.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum CompletableFutureSimpleThreadPool {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(CompletableFutureSimpleThreadPool.class);
    private static final long SHUTDOWN_TIMEOUT_SECONDS = 60;

    private final ThreadPoolExecutor singleThreadPool;

    CompletableFutureSimpleThreadPool() {
        singleThreadPool = new ThreadPoolExecutor(
                15, 20,
                30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                r -> new Thread(r, "CompletableFutureUtils-" + r.hashCode())
        );

        // JVM 关闭时自动关闭线程池
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    private void logTaskExecution(Runnable action) {
        long startTime = System.currentTimeMillis();
        log.info("[线程池][执行任务] 开始时间：{}", LocalDateTimeUtil.now());
        logThreadPoolStatus();

        try {
            action.run();
        } catch (Exception e) {
            log.error("任务执行失败", e);
        } finally {
            log.info("[线程池][执行任务] 结束时间：{}，执行任务结束，耗时:{}ms",
                    LocalDateTimeUtil.now(), System.currentTimeMillis() - startTime);
        }
    }

    public static void executeTasks(@NotNull Runnable... tasks) {
        if (ObjectUtil.isEmpty(tasks)) {
            log.error("[线程池][执行任务] 任务为空");
            return;
        }

        INSTANCE.logTaskExecution(() -> {
            CompletableFuture<?>[] futures = Arrays.stream(tasks)
                    .map(task -> CompletableFuture.runAsync(task, INSTANCE.singleThreadPool))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
        });
    }

    @SafeVarargs
    public static <T> List<T> executeCompletableFutures(@NotNull CompletableFuture<T>... tasks) {
        if (ObjectUtil.isEmpty(tasks)) {
            log.error("[线程池][执行任务] 任务为空");
            return Collections.emptyList();
        }

        return INSTANCE.logTaskExecutionWithResult(() -> {
            CompletableFuture<Void> allOf = CompletableFuture.allOf(tasks);
            allOf.join();

            return Arrays.stream(tasks)
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        });
    }

    @SafeVarargs
    public static <T> List<T> executeSuppliers(@NotNull Supplier<T>... tasks) {
        if (ObjectUtil.isEmpty(tasks)) {
            log.error("[线程池][执行任务] 任务为空");
            return Collections.emptyList();
        }

        return INSTANCE.logTaskExecutionWithResult(() -> {
            List<CompletableFuture<T>> futures = Arrays.stream(tasks)
                    .map(task -> CompletableFuture.supplyAsync(task, INSTANCE.singleThreadPool))
                    .collect(Collectors.toList());

            return futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        });
    }

    private <T> T logTaskExecutionWithResult(Supplier<T> action) {
        long startTime = System.currentTimeMillis();
        log.info("[线程池][执行任务] 开始时间：{}", LocalDateTimeUtil.now());
        logThreadPoolStatus();

        try {
            return action.get();
        } catch (Exception e) {
            log.error("任务执行失败", e);
            return null;
        } finally {
            log.info("[线程池][执行任务] 结束时间：{}，执行任务结束，耗时:{}ms",
                    LocalDateTimeUtil.now(), System.currentTimeMillis() - startTime);
        }
    }

    private void logThreadPoolStatus() {
        log.info("[线程池状态] 池大小:{}，活跃线程数:{}，排队任务数:{}，总任务数:{}，完成任务数:{}",
                singleThreadPool.getPoolSize(),
                singleThreadPool.getActiveCount(),
                singleThreadPool.getQueue().size(),
                singleThreadPool.getTaskCount(),
                singleThreadPool.getCompletedTaskCount());
    }

    public void shutdown() {
        log.info("[线程池] 正在关闭线程池...");
        singleThreadPool.shutdown();
        try {
            if (!singleThreadPool.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                log.warn("[线程池] 线程池未在规定时间内关闭，强制关闭");
                singleThreadPool.shutdownNow();
            }
            log.info("[线程池] 线程池已关闭");
        } catch (InterruptedException e) {
            log.error("[线程池] 线程池关闭时被中断", e);
            singleThreadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}