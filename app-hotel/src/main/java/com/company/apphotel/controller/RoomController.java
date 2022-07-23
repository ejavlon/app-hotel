package com.company.apphotel.controller;

import com.company.apphotel.entity.Hotel;
import com.company.apphotel.entity.Room;
import com.company.apphotel.payload.RoomDto;
import com.company.apphotel.repository.HotelRepository;
import com.company.apphotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return null;
        return optionalRoom.get();
    }

    @GetMapping("/{hotelId}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotelId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        return roomRepository.findByHotelId(hotelId,pageable);
    }

    @GetMapping
    public Page<Room> getRooms(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        return roomRepository.findAll(pageable);
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) return "Hotel not found";

        boolean existsRoom = roomRepository.existsByHotelAndNumber(optionalHotel.get(), roomDto.getNumber());
        if (existsRoom) return "There is such a room";

        Room room = new Room();
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room seccessfully added";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return "Room not found";

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) return "Hotel not found";

        boolean existsRoom = roomRepository.existsByHotelAndNumber(optionalHotel.get(), roomDto.getNumber());
        if (!existsRoom) return "No such room available";

        Room room = optionalRoom.get();
        room.setFloor(roomDto.getFloor());
        room.setNumber(roomDto.getNumber());
        room.setSize(roomDto.getSize());
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room seccessfully edited";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) return "Room not found";
        roomRepository.deleteById(id);
        return "Room seccessfully deleted";
    }
}
