package com.septeo.ulyses.technical.test.repository;

import com.septeo.ulyses.technical.test.entity.Brand;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.entity.Vehicle;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Sales entity.
 */
@Repository
public interface SalesRepository {

    /**
     * Find paginated sales.
     */
    List<Sales> findAll(int page, int size);

    /**
     * Find a sale by its ID.
     *
     * @param id the ID of the sale to find
     * @return an Optional containing the sale if found, or empty if not found
     */
    Optional<Sales> findById(Long id);

    /**
     * Find sales by brand ID.
     */
    List<Sales> findByBrandId(Long brandId);

    /**
     * Find sales by vehicle ID.
     */
    List<Sales> findByVehicleId(Long vehicleId);

}
