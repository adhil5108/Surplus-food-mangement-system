package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackingResponse {
    private Double lat;
    private Double lng;
    private String status;
}
