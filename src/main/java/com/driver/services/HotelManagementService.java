package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelManagementRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HotelManagementService {

    HotelManagementRepository hotelManagementRepository=new HotelManagementRepository();

    public String addHotel(Hotel hotel){
        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user){
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities(){
        List<Hotel>hotels=hotelManagementRepository.getHotelWithMostFacilities();
        int n1=0;
        for(Hotel hotel:hotels){
            int n2=hotel.getFacilities().size();
            if(n2>n1)
                n1=n2;
        }
        if(n1==0)return"";
        List<String>hotelName=new ArrayList<>();
        for(Hotel hotel:hotels){
            if(hotel.getFacilities().size()==n1){
                hotelName.add(hotel.getHotelName());
            }
        }
        if(hotelName.size()==1)return hotelName.get(0);
        Collections.sort(hotelName);
        return hotelName.get(0);
    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        booking.setBookingId(uuidString);
        List<Hotel>hotels=hotelManagementRepository.getHotelWithMostFacilities();
        String hotelName= booking.getHotelName();
        int reqdRooms=booking.getNoOfRooms();
        int availableRooms=0,prices=0;
        for(Hotel hotel:hotels){
            if(hotel.getHotelName().equals(hotelName)){
                 availableRooms=hotel.getAvailableRooms();
                 prices=hotel.getPricePerNight();
            }
        }
        int amountPaid=-1;
        if(availableRooms>=reqdRooms){
            amountPaid=reqdRooms*prices;
            booking.setAmountToBePaid(amountPaid);
            hotelManagementRepository.bookARoom(booking);
            return amountPaid;
        }
        booking.setAmountToBePaid(amountPaid);
        hotelManagementRepository.bookARoom(booking);
        return amountPaid;
    }

    public int getBookings(int aadharCard){
        List<Booking>bookings=hotelManagementRepository.getBookings();
        List<Hotel>hotels=hotelManagementRepository.getHotelWithMostFacilities();
        int amount=0,price=0;
        String name="";
        for(Booking booking:bookings){
            if(booking.getBookingAadharCard()==aadharCard){
                amount=booking.getAmountToBePaid();
                name=booking.getHotelName();
            }
        }
        for(Hotel hotel:hotels){
            if(hotel.getHotelName().equals(name)){
                price=hotel.getPricePerNight();
            }
        }
        return amount/price;
    }
}
