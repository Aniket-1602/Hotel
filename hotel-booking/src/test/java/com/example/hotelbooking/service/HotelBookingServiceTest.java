package com.example.hotelbooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.hotelbooking.dao.HotelBooking;
import com.example.hotelbooking.repository.HotelBookingRepository;

class HotelBookingServiceTest {

    @Mock
    private HotelBookingRepository bookingRepository;

    @InjectMocks
    private HotelBookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBookings() {
        List<HotelBooking> bookings = new ArrayList<>();
        bookings.add(new HotelBooking(1L, "John Doe", "Hotel A", null, null));
        bookings.add(new HotelBooking(2L, "Jane Smith", "Hotel B", null, null));
        
        when(bookingRepository.findAll()).thenReturn(bookings);
        
        List<HotelBooking> result = bookingService.getAllBookings();
        
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testGetBookingById() {
        Long bookingId = 1L;
        HotelBooking booking = new HotelBooking(bookingId, "John Doe", "Hotel A", null, null);
        
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        
        HotelBooking result = bookingService.getBookingById(bookingId);
        
        assertNotNull(result);
        assertEquals(bookingId, result.getId());
    }

    @Test
    void testCreateBooking() {
        HotelBooking booking = new HotelBooking(null, "John Doe", "Hotel A", null, null);
        
        when(bookingRepository.save(any())).thenReturn(booking);
        
        HotelBooking result = bookingService.createBooking(booking);
        
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("John Doe", result.getGuestName());
        assertEquals("Hotel A", result.getHotelName());
    }

    @Test
    void testUpdateBooking() {
        Long bookingId = 1L;
        HotelBooking updatedBooking = new HotelBooking(bookingId, "Jane Smith", "Hotel B", null, null);
        
        when(bookingRepository.existsById(bookingId)).thenReturn(true);
        when(bookingRepository.save(updatedBooking)).thenReturn(updatedBooking);
        
        HotelBooking result = bookingService.updateBooking(bookingId, updatedBooking);
        
        assertNotNull(result);
        assertEquals("Jane Smith", result.getGuestName());
        assertEquals("Hotel B", result.getHotelName());
    }

    @Test
    void testDeleteBooking() {
        Long bookingId = 1L;
        
        when(bookingRepository.existsById(bookingId)).thenReturn(true);
        
        boolean result = bookingService.deleteBooking(bookingId);
        
        assertTrue(result);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    void testDeleteBookingNotFound() {
        Long bookingId = 1L;
        
        when(bookingRepository.existsById(bookingId)).thenReturn(false);
        
        boolean result = bookingService.deleteBooking(bookingId);
        
        assertFalse(result);
        verify(bookingRepository, times(0)).deleteById(bookingId);
    }
}
