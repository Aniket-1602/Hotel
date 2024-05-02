package com.example.hotelbooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelbooking.dao.HotelBooking;
import com.example.hotelbooking.service.HotelBookingService;

/**
 * Controller class for managing hotel bookings.
 *
 * @author Aniket
 */

@RestController
@RequestMapping("/api/bookings")
public class HotelBookingController {
	@Autowired
	private HotelBookingService bookingService;

	/**
	 * Retrieves all hotel bookings.
	 * 
	 * @return a list of all hotel bookings.
	 */

	@GetMapping
	public List<HotelBooking> getAllBookings() {
		return bookingService.getAllBookings();
	}

	/**
	 * Retrieves a hotel booking by its ID.
	 * 
	 * @param id The ID of the booking to retrieve.
	 * @return ResponseEntity containing the hotel booking if found, or empty if not
	 *         found.
	 */

	@GetMapping("/{id}")
	public ResponseEntity<HotelBooking> getBookingById(@PathVariable Long id) {
		HotelBooking booking = bookingService.getBookingById(id);
		return ResponseEntity.of(Optional.ofNullable(booking));
	}

	/**
	 * Creates a new hotel booking.
	 * 
	 * @param booking The booking to create.
	 * @return ResponseEntity containing the created hotel booking.
	 */
	@PostMapping
	public ResponseEntity<HotelBooking> createBooking(@RequestBody HotelBooking booking) {
		HotelBooking createdBooking = bookingService.createBooking(booking);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
	}

	/**
	 * Updates an existing hotel booking.
	 * 
	 * @param id             The ID of the booking to update.
	 * @param updatedBooking The updated booking information.
	 * @return ResponseEntity containing the updated hotel booking if successful, or
	 *         not found if the booking doesn't exist.
	 */

	@PutMapping("/{id}")
	public ResponseEntity<HotelBooking> updateBooking(@PathVariable Long id, @RequestBody HotelBooking updatedBooking) {
		HotelBooking result = bookingService.updateBooking(id, updatedBooking);
		if (result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Deletes a hotel booking.
	 * 
	 * @param id The ID of the booking to delete.
	 * @return ResponseEntity indicating success if the booking was deleted, or not
	 *         found if the booking doesn't exist.
	 */

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		if (bookingService.deleteBooking(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
