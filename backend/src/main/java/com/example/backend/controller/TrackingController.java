package com.example.backend.controller;

import com.example.backend.dto.TrackingResponse;
import com.example.backend.model.FoodPost;
import com.example.backend.repository.FoodPostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/track")
@RequiredArgsConstructor
public class TrackingController {

    private final FoodPostRepo foodPostRepo;

    @GetMapping("/{foodId}")
    public TrackingResponse getLocation(@PathVariable Long foodId) {

        FoodPost post = foodPostRepo.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        return new TrackingResponse(
                post.getDriverLat(),
                post.getDriverLng(),
                post.getStatus().name()
        );
    }

}
