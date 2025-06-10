package com.septeo.ulyses.technical.test.controller;

import com.septeo.ulyses.technical.test.dto.VehicleSalesDTO;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @GetMapping
    public ResponseEntity<List<Sales>> getAllSales(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(salesService.getAllSales(page, 10));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSalesById(@PathVariable Long id) {
        return salesService.getSalesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<List<Sales>> getSalesByBrandId(@PathVariable Long brandId) {
        return ResponseEntity.ok(salesService.getSalesByBrandId(brandId));
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<List<Sales>> getSalesByVehicleId(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(salesService.getSalesByVehicleId(vehicleId));
    }

    @GetMapping("/vehicles/bestSelling")
    public ResponseEntity<List<VehicleSalesDTO>> getBestSellingVehicles(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<VehicleSalesDTO> bestSellingVehicles = salesService.getBestSellingVehicles(startDate, endDate);
        return ResponseEntity.ok(bestSellingVehicles);
    }

}
