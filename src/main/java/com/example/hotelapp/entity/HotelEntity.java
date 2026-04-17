package com.example.hotelapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@ToString(exclude = {"address", "contacts", "arrivalTime", "amenities"})
public class HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "CLOB")
    private String description;

    @Column(name = "brand", nullable = false, length = 255)
    private String brand;

    @OneToOne(
            mappedBy = "hotel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            optional = false
    )
    private HotelAddressEntity address;

    @OneToOne(
            mappedBy = "hotel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            optional = false
    )
    private HotelContactsEntity contacts;

    @OneToOne(
            mappedBy = "hotel",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            optional = false
    )
    private HotelArrivalTimeEntity arrivalTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<AmenityEntity> amenities = new LinkedHashSet<>();

    public void setAddress(HotelAddressEntity address) {
        this.address = address;
        if (address != null) {
            address.setHotel(this);
        }
    }

    public void setContacts(HotelContactsEntity contacts) {
        this.contacts = contacts;
        if (contacts != null) {
            contacts.setHotel(this);
        }
    }

    public void setArrivalTime(HotelArrivalTimeEntity arrivalTime) {
        this.arrivalTime = arrivalTime;
        if (arrivalTime != null) {
            arrivalTime.setHotel(this);
        }
    }

    public void addAmenity(AmenityEntity amenity) {
        this.amenities.add(amenity);
        amenity.getHotels().add(this);
    }

    public void removeAmenity(AmenityEntity amenity) {
        this.amenities.remove(amenity);
        amenity.getHotels().remove(this);
    }
}
