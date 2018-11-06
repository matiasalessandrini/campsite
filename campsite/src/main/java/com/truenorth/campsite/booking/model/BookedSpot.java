package com.truenorth.campsite.booking.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.truenorth.campsite.spot.model.Spot;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"spot_id","date"})})
public class BookedSpot {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Spot spot;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;
	
	private Date date;
	
	private BookedSpot() {}
	
	public BookedSpot(Spot spot, Date date) {
		this.spot = spot;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
}
