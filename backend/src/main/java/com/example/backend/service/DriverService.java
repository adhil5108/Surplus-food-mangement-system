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
public class DriverService {

    private final FoodPostRepo foodPostRepo;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public List<FoodPost> getMyDeliveries(Users driver) {
        return foodPostRepo.findByAssignedDriverAndStatusNot(
                driver,
                FoodStatus.DELIVERED
        );
    }

    public FoodPost updateLocation(Users driver, Double lat, Double lng) throws Exception {
        FoodPost post = foodPostRepo.findFirstByAssignedDriver(driver)
                .orElseThrow(() -> new Exception("No assigned delivery"));

        post.setDriverLat(lat);
        post.setDriverLng(lng);

        return foodPostRepo.save(post);
    }

    public FoodPost updateChecklist(Users driver) throws Exception {
        FoodPost post = foodPostRepo.findFirstByAssignedDriver(driver)
                .orElseThrow(() -> new Exception("No assigned delivery"));

        if (post.getStatus() == FoodStatus.DRIVER_ASSIGNED) {
            post.setStatus(FoodStatus.PICKED_UP);
            notifyAdmins("Food #" + post.getId() + " picked up by driver.");
        }

        else if (post.getStatus() == FoodStatus.PICKED_UP) {
            post.setStatus(FoodStatus.DELIVERED);
            notifyAdmins("Food #" + post.getId() + " delivered successfully.");
        }

        return foodPostRepo.save(post);
    }

    private void notifyAdmins(String message) {
        List<Users> admins = userRepository.findByRole(Role.ADMIN);
        for (Users admin : admins) {
            notificationService.send(admin.getId(), message);
        }
    }
}
