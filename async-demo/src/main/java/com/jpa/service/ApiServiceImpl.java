package com.jpa.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    Map<String, CompletableFuture<String>> dataFutures = new ConcurrentHashMap<>();
    private final int maxRetry = 3;
    private final int retryTimeout = 3000;
    private final int timeout = 9000;

    @Override
    public String getDataAsync() {
        String taskId = UUID.randomUUID().toString();
        var task = new CompletableFuture<String>();
        task.completeOnTimeout("Timeout excute", timeout, TimeUnit.MILLISECONDS);
        task.whenComplete((result, exception) -> {
            log.info("task complete {}", result);
            dataFutures.remove(taskId);
        });
        dataFutures.put(taskId, task);
        for (int i = 0; i < maxRetry; i++) {
            try {
//                task.complete("Success!");
                return task.get(retryTimeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                log.error("Timout when retry {}", i == 0 ? "" : "retry=" + i);
            }
        }
        return null;
    }
}
