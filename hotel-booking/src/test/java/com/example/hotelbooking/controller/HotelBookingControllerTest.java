package com.example.hotelbooking.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hotelbooking.dao.HotelBooking;
import com.example.hotelbooking.service.HotelBookingService;

class HotelBookingControllerTest {

    @Mock
    private HotelBookingService bookingService;

    @InjectMocks
    private HotelBookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBookings() {
        List<HotelBooking> bookings = new ArrayList<>();
        bookings.add(new HotelBooking(1L, "John Doe", "Hotel A", LocalDate.now(), LocalDate.now().plusDays(3)));
        bookings.add(new HotelBooking(2L, "Jane Smith", "Hotel B", LocalDate.now().plusDays(1), LocalDate.now().plusDays(4)));
        
        when(bookingService.getAllBookings()).thenReturn(bookings);
        
        List<HotelBooking> result = bookingController.getAllBookings();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetBookingById() {
        Long bookingId = 1L;
        HotelBooking booking = new HotelBooking(bookingId, "John Doe", "Hotel A", LocalDate.now(), LocalDate.now().plusDays(3));
        
        when(bookingService.getBookingById(bookingId)).thenReturn(booking);
        
        ResponseEntity<HotelBooking> response = bookingController.getBookingById(bookingId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookingId, response.getBody().getId());
    }

    @Test
    void testCreateBooking() {
        HotelBooking booking = new HotelBooking(null, "John Doe", "Hotel A", LocalDate.now(), LocalDate.now().plusDays(3));
        HotelBooking createdBooking = new HotelBooking(1L, "John Doe", "Hotel A", LocalDate.now(), LocalDate.now().plusDays(3));
        
        when(bookingService.createBooking(any(HotelBooking.class))).thenReturn(createdBooking);
        
        ResponseEntity<HotelBooking> response = bookingController.createBooking(booking);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testUpdateBooking() {
        Long bookingId = 1L;
        HotelBooking updatedBooking = new HotelBooking(bookingId, "Jane Smith", "Hotel B", LocalDate.now().plusDays(1), LocalDate.now().plusDays(4));
        
        when(bookingService.updateBooking(bookingId, updatedBooking)).thenReturn(updatedBooking);
        
        ResponseEntity<HotelBooking> response = bookingController.updateBooking(bookingId, updatedBooking);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane Smith", response.getBody().getGuestName());
        assertEquals("Hotel B", response.getBody().getHotelName());
    }

    @Test
    void testDeleteBooking() {
        Long bookingId = 1L;
        
        when(bookingService.deleteBooking(bookingId)).thenReturn(true);
        
        ResponseEntity<Void> response = bookingController.deleteBooking(bookingId);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}
