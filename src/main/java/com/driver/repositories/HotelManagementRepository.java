package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelManagementRepository {

    Map<String, Hotel> hotelDb=new HashMap<>();
    Map<Integer, User> userDb=new HashMap<>();
    Map<String, Booking> bookingDb=new HashMap<>();
    public String addHotel(Hotel hotel){
        String name=hotel.getHotelName();
        if(name.length()==0 || hotel==null)
            return "Failure name can't be null";
        if(hotelDb.containsKey(name))
            return "Failure duplicate name";
        hotelDb.put(name,hotel);
        return "Hotel added successfully";
    }

    public Integer addUser(User user){
        int no=user.getaadharCardNo();
        userDb.put(no,user);
        return no;
    }

    public List<Hotel> getHotelWithMostFacilities(){
        List<Hotel>hotel= new ArrayList<>();
        for(String name: hotelDb.keySet()){
            hotel.add(hotelDb.get(name));
        }
        return hotel;
    }


    public void bookARoom(Booking booking){
       String id=booking.getBookingId();
       bookingDb.put(id,booking);
    }

    public List<Booking> getBookings(){
        List<Booking>booking= new ArrayList<>();
        for(String booking1:bookingDb.keySet()){
            booking.add(bookingDb.get(booking1));
        }
        return booking;
    }
}
