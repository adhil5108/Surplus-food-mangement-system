package com.example.backend.service;

import com.example.backend.model.FoodPost;
import com.example.backend.model.FoodStatus;
import com.example.backend.model.Role;
import com.example.backend.model.Users;
import com.example.backend.repository.FoodPostRepo;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final FoodPostRepo foodPostRepo;
    private final UserRepository userRepo;

    public List<FoodPost> getAllFoods() {
        return foodPostRepo.findAll();
    }

    public List<Users> getAllDrivers() {
        return userRepo.findByRole(Role.valueOf("DRIVER"));
    }

    public FoodPost assignDriver(Long postId, Long driverId) throws Exception {

        FoodPost post = foodPostRepo.findById(postId)
                .orElseThrow(() -> new Exception("Food post not found"));

        Users driver = userRepo.findById(driverId)
                .orElseThrow(() -> new Exception("Driver not found"));

        if (!driver.getRole().name().equals("DRIVER")) {
            throw new Exception("Selected user is not a driver");
        }

        post.setAssignedDriver(driver);
        post.setStatus(FoodStatus.DRIVER_ASSIGNED);

        return foodPostRepo.save(post);
    }
}
