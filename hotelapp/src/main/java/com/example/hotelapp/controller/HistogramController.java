package com.example.hotelapp.controller;

import com.example.hotelapp.service.HistogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/histogram")
@RequiredArgsConstructor
@Tag(name = "Histogram", description = "Histogram and aggregation operations")
public class HistogramController {

    private final HistogramService histogramService;

    @Operation(summary = "Get histogram grouped by parameter")
    @GetMapping("/{param}")
    public Map<String, Long> getHistogram(@PathVariable String param){
        return histogramService.getHistogram(param);
    }
}
