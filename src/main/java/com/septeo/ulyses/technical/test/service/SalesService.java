package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Sales operations.
 */
public interface SalesService {

    /**
     * Get all sales with pagination.
     *
     * @param page the page number (0-based)
     * @param size the number of items per page
     * @return a list of sales for the given page
     */
    List<Sales> getAllSales(int page, int size);

    /**
     * Get a sales by its ID.
     *
     * @param id the ID of the sales to find
     * @return an Optional containing the sales if found, or empty if not found
     */
    Optional<Sales> getSalesById(Long id);

    /**
     * Get all sales for a specific brand.
     *
     * @param brandId the brand ID
     * @return a list of sales for the given brand
     */
    List<Sales> getSalesByBrandId(Long brandId);

    /**
     * Get all sales for a specific vehicle.
     *
     * @param vehicleId the vehicle ID
     * @return a list of sales for the given vehicle
     */
    List<Sales> getSalesByVehicleId(Long vehicleId);

}
