package com.example.hotelapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "hotel_contacts")
@Getter
@Setter
@ToString(exclude = "hotel")
public class HotelContactsEntity {

    @Id
    @Column(name = "hotel_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @Column(name = "phone", nullable = false, length = 100)
    private String phone;

    @Column(name = "email", nullable = false, length = 255)
    private String email;
}