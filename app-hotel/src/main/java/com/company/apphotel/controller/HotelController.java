package com.company.apphotel.controller;

import com.company.apphotel.entity.Hotel;
import com.company.apphotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) return null;
        return optionalHotel.get();
    }

    @GetMapping
    public Page<Hotel> getHotels(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        return hotelRepository.findAll(pageable);
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists) return "There is such a hotel";
        hotelRepository.save(hotel);
        return "Hotel seccessfully saved";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) return "Hotel not found";
        Hotel editedHotel = optionalHotel.get();
        editedHotel.setName(hotel.getName());
        hotelRepository.save(editedHotel);
        return "Hotel seccessfully edited";
    }
    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (!optionalHotel.isPresent()) return "Hotel not found";
        hotelRepository.delete(optionalHotel.get());
        return "Hotel seccessfully deleted";
    }
}
