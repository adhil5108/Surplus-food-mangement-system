package com.example.backend.service;

import com.example.backend.dto.FoodPostRequest;
import com.example.backend.model.FoodPost;
import com.example.backend.model.FoodStatus;
import com.example.backend.model.Role;
import com.example.backend.model.Users;
import com.example.backend.repository.FoodPostRepo;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonorService {

    private final FoodPostRepo foodPostRepo;
    private final FileStorageService fileStorageService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public FoodPost createFood(FoodPostRequest request, Users donor, MultipartFile image) throws Exception {

        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            String filename = fileStorageService.storeFile(image);
            imageUrl = "/uploads/" + filename;
        }

        FoodPost post = FoodPost.builder()
                .title(request.getTitle())
                .quantity(request.getQuantity())
                .pickupTime(request.getPickupTime())
                .location(request.getLocation())
                .status(FoodStatus.PENDING)
                .donor(donor)
                .imageUrl(imageUrl)
                .build();

        FoodPost savedPost = foodPostRepo.save(post);


        userRepository.findByRole(Role.NGO).forEach(ngo -> {
            notificationService.send(
                    ngo.getId(),
                    "New food posted: " + savedPost.getTitle()
            );
        });

        return savedPost;
    }

    public List<FoodPost> getMyFoods(Users donor) {
        return foodPostRepo.findByDonor(donor);
    }
}
