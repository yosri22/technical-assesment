package com.septeo.ulyses.technical.test.service;

import com.septeo.ulyses.technical.test.dto.VehicleSalesDTO;
import com.septeo.ulyses.technical.test.entity.Sales;
import com.septeo.ulyses.technical.test.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

/**
 * Implementation of the SalesService interface.
 * This class provides the implementation for all sales-related operations.
 */
@Service
@Transactional(readOnly = false)
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getAllSales(int page, int size) {
        return salesRepository.findAll(page, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Sales> getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByBrandId(Long brandId) {
        return salesRepository.findByBrandId(brandId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sales> getSalesByVehicleId(Long vehicleId) {
        return salesRepository.findByVehicleId(vehicleId);
    }

    @Override
    public List<VehicleSalesDTO> getBestSellingVehicles(LocalDate startDate, LocalDate endDate) {
        List<Sales> allSales = salesRepository.findAll(0, Integer.MAX_VALUE);

        List<Sales> filteredSales = allSales.stream()
                .filter(s -> (startDate == null || !s.getSaleDate().isBefore(startDate)) &&
                        (endDate == null || !s.getSaleDate().isAfter(endDate)))
                .toList();

        Map<Long, VehicleSalesDTO> vehicleSalesMap = new HashMap<>();
        for (Sales sale : filteredSales) {
            Long vehicleId = sale.getVehicle().getId();
            String model = sale.getVehicle().getModel();

            vehicleSalesMap.compute(vehicleId, (key, existing) -> {
                if (existing == null) {
                    return new VehicleSalesDTO(vehicleId, model, 1);
                } else {
                    return new VehicleSalesDTO(vehicleId, model, existing.totalSales() + 1);
                }
            });
        }

        List<VehicleSalesDTO> topList = new ArrayList<>();

        for (VehicleSalesDTO dto : vehicleSalesMap.values()) {
            boolean inserted = false;
            for (int i = 0; i < topList.size(); i++) {
                if (dto.totalSales() > topList.get(i).totalSales()) {
                    topList.add(i, dto);
                    inserted = true;
                    break;
                }
            }
            if (!inserted && topList.size() < 5) {
                topList.add(dto);
            }
            if (topList.size() > 5) {
                topList.remove(topList.size() - 1);
            }
        }

        return topList;
    }
}
