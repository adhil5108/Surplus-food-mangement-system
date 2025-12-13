package com.example.backend.controller;

import com.example.backend.model.FoodPost;
import com.example.backend.model.Users;
import com.example.backend.model.Role;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/all-foods")
    public List<FoodPost> getAllFoods() {
        return adminService.getAllFoods();
    }

    @GetMapping("/drivers")
    public List<Users> getDrivers() {
        return adminService.getAllDrivers();
    }

    @PatchMapping("/assign-driver/{postId}")
    public FoodPost assignDriver(@PathVariable Long postId, @RequestParam Long driverId) throws Exception {
        // you can also verify admin user if needed
        return adminService.assignDriver(postId, driverId);
    }
}
