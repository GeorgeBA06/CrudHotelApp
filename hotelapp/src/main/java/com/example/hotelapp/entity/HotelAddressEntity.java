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
@Table(name = "hotel_addresses")
@Getter
@Setter
@ToString(exclude = "hotel")
public class HotelAddressEntity {

    @Id
    @Column(name = "hotel_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @Column(name = "house_number", nullable = false)
    private Integer houseNumber;

    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @Column(name = "country", nullable = false, length = 255)
    private String country;

    @Column(name = "post_code", nullable = false, length = 50)
    private String postCode;
}