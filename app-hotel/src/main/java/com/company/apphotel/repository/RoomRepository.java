package com.company.apphotel.repository;

import com.company.apphotel.entity.Hotel;
import com.company.apphotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    Page<Room> findByHotelId(Integer hotel_id, Pageable pageable);

    boolean existsByHotelAndNumber(Hotel hotel, Integer number);
}
