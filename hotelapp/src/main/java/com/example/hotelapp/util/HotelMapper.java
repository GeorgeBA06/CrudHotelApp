package com.example.hotelapp.util;

import com.example.hotelapp.dto.request.AddressRequestDto;
import com.example.hotelapp.dto.request.ArrivalTimeRequestDto;
import com.example.hotelapp.dto.request.ContactsRequestDto;
import com.example.hotelapp.dto.request.CreateHotelRequestDto;
import com.example.hotelapp.dto.response.*;
import com.example.hotelapp.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelMapper {

    public HotelShortResponseDto toShortResponse(HotelEntity hotelEntity){
        return new HotelShortResponseDto(
                hotelEntity.getId(),
                hotelEntity.getName(),
                hotelEntity.getDescription(),
                formatAddress(hotelEntity.getAddress()),
                hotelEntity.getContacts() != null ? hotelEntity.getContacts().getPhone() : null

        );
    }

    public HotelDetailsResponseDto toDetailsResponse(HotelEntity hotelEntity){
        return new HotelDetailsResponseDto(
                hotelEntity.getId(),
                hotelEntity.getName(),
                hotelEntity.getBrand(),
                hotelEntity.getDescription(),
                toAddressResponse(hotelEntity.getAddress()),
                toContactsResponse(hotelEntity.getContacts()),
                toArrivalTimeResponse(hotelEntity.getArrivalTime()),
                hotelEntity.getAmenities() == null
                ? List.of()
                        : hotelEntity.getAmenities().stream()
                        .map(AmenityEntity::getName)
                        .sorted()
                        .toList()
        );
    }

    public HotelEntity toHotelEntity(CreateHotelRequestDto requestDto){
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setName(hotelEntity.getName());
        hotelEntity.setDescription(hotelEntity.getDescription());
        hotelEntity.setBrand(hotelEntity.getBrand());
        hotelEntity.setAddress(toAddressEntity(requestDto.addressRequestDto()));
        hotelEntity.setContacts(toContactsEntity(requestDto.contactsRequestDto()));
        hotelEntity.setArrivalTime(toArrivalTimeEntity(requestDto.arrivalTimeRequestDto()));

        return hotelEntity;
    }


    public AddressResponseDto toAddressResponse(HotelAddressEntity address) {
        if (address == null) {
            return null;
        }

        return new AddressResponseDto(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );
    }

    public ContactsResponseDto toContactsResponse(HotelContactsEntity contacts) {
        if (contacts == null) {
            return null;
        }

        return new ContactsResponseDto(
                contacts.getPhone(),
                contacts.getEmail()
        );
    }

    public ArrivalTimeResponseDto toArrivalTimeResponse(HotelArrivalTimeEntity arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }

        return new ArrivalTimeResponseDto(
                arrivalTime.getCheckIn(),
                arrivalTime.getCheckOut()
        );
    }

    public HotelAddressEntity toAddressEntity(AddressRequestDto requestDto){
        if(requestDto == null){
            return null;
        }
        HotelAddressEntity hotelAddress = new HotelAddressEntity();
        hotelAddress.setHouseNumber(requestDto.houseNumber());
        hotelAddress.setStreet(requestDto.street());
        hotelAddress.setCity(requestDto.city());
        hotelAddress.setCountry(requestDto.country());
        hotelAddress.setPostCode(requestDto.postCode());

        return hotelAddress;
    }

    public HotelContactsEntity toContactsEntity(ContactsRequestDto request) {
        if (request == null) {
            return null;
        }

        HotelContactsEntity entity = new HotelContactsEntity();
        entity.setPhone(request.phone());
        entity.setEmail(request.email());

        return entity;
    }

    public HotelArrivalTimeEntity toArrivalTimeEntity(ArrivalTimeRequestDto request) {
        if (request == null) {
            return null;
        }

        HotelArrivalTimeEntity entity = new HotelArrivalTimeEntity();
        entity.setCheckIn(request.checkIn());
        entity.setCheckOut(request.checkOut());

        return entity;
    }

    private String formatAddress(HotelAddressEntity address){
        if(address == null){
            return null;
        }

        return String.format(
                "%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }
}
