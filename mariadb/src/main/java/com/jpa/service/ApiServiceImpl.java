package com.jpa.service;

import com.jpa.entity.RentalNewEntity;
import com.jpa.repository.RentalNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final RentalNewRepository rentalNewRepository;

    @Override
    @Transactional
    public void importExcel() {
        var data = new ArrayList<RentalNewEntity>();
        for (int i = 0; i < 50000; i++) {
            var entity = new RentalNewEntity()
                    .setRentalDate(LocalDateTime.now())
                    .setInventoryId(i)
                    .setCustomerId(i)
                    .setRentalDate(LocalDateTime.now())
                    .setStaffId(111)
                    .setLastUpdate(LocalDateTime.now())
                    .setStatus("NEW");
            data.add(entity);
        }
        rentalNewRepository.saveAll(data);
    }

    @Override
    public <T> T getData() {
        return (T) rentalNewRepository.findById(198102).get();
    }

//    CompletableFuture<String> task = new CompletableFuture<String>();
//
//    public String getData() {
//        return task.get();
//    }

//    public void consume() {
//        task.complete("success");
//    }

}
