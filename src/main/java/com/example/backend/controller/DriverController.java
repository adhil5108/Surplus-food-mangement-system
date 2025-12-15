package com.example.backend.controller;

import com.example.backend.model.FoodPost;
import com.example.backend.model.Users;
import com.example.backend.security.CustomUserDetails;
import com.example.backend.service.DriverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DriverController {

    private final DriverService driverService;

    private Users getDriver() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser();
    }


    @GetMapping("/my-deliveries")
    public List<FoodPost> getMyDeliveries() {
        return driverService.getMyDeliveries(getDriver());
    }


    @PostMapping("/update-location")
    public FoodPost updateLocation(
            @RequestParam String livelocation
    ) throws Exception {
        return driverService.updateLocation(getDriver(), livelocation);
    }



    @PostMapping("/checklist")
    public FoodPost updateChecklist() throws Exception {
        return driverService.updateChecklist(getDriver());
    }
}
