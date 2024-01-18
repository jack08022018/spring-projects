package com.jpa.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "rental_new")
public class RentalNewEntity implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "rental_seq", sequenceName = "rental_seq",allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
    @Column(name = "id")
    private Integer id;

    @Column(name = "rental_date")
    private LocalDateTime rentalDate;

    @Column(name = "inventory_id")
    private Integer inventoryId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "staff_id")
    private Integer staffId;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "status")
    private String status;

}
