package com.example.hotelbooking.service;

import java.util.List;

/**
 * Service class for managing hotel bookings.
 * @author Aniket
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotelbooking.dao.HotelBooking;
import com.example.hotelbooking.repository.HotelBookingRepository;

@Service
public class HotelBookingService {

	@Autowired
	private HotelBookingRepository bookingRepository;

	/**
	 * Retrieves all hotel bookings.
	 * 
	 * @return A list of all hotel bookings.
	 */

	public List<HotelBooking> getAllBookings() {
		return bookingRepository.findAll();
	}

	/**
	 * Retrieves a hotel booking by its ID.
	 * 
	 * @param id The ID of the booking to retrieve.
	 * @return The hotel booking if found, or null if not found.
	 */

	public HotelBooking getBookingById(Long id) {
		return bookingRepository.findById(id).orElse(null);
	}

	/**
	 * Creates a new hotel booking.
	 * 
	 * @param booking The booking to create.
	 * @return The created hotel booking.
	 */

	public HotelBooking createBooking(HotelBooking booking) {
		return bookingRepository.save(booking);
	}

	/**
	 * Updates an existing hotel booking.
	 * 
	 * @param id             The ID of the booking to update.
	 * @param updatedBooking The updated booking information.
	 * @return The updated hotel booking if successful, or null if the booking
	 *         doesn't exist.
	 */
	public HotelBooking updateBooking(Long id, HotelBooking updatedBooking) {
		if (bookingRepository.existsById(id)) {
			updatedBooking.setId(id);
			return bookingRepository.save(updatedBooking);
		}
		return null;
	}

	/**
	 * Deletes a hotel booking.
	 * 
	 * @param id The ID of the booking to delete.
	 * @return true if the booking was deleted successfully, false if the booking
	 *         doesn't exist.
	 */
	public boolean deleteBooking(Long id) {
		if (bookingRepository.existsById(id)) {
			bookingRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
