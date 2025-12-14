package com.example.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(30 * 60 * 1000L);

        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        new Thread(() -> {
            while (true) {
                try {
                    emitter.send(
                            SseEmitter.event()
                                    .name("ping")
                                    .data("checking")
                    );
                    Thread.sleep(15000);
                } catch (Exception e) {
                    emitters.remove(userId);
                    break;
                }
            }
        }).start();

        return emitter;
    }

    public void send(Long userId, Object data) {
        SseEmitter emitter = emitters.get(userId);

        if (emitter != null) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("notification")
                                .data(data)
                );
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }
}
