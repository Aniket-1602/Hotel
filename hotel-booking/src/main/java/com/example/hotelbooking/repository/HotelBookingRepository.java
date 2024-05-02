package com.example.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotelbooking.dao.HotelBooking;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
}
