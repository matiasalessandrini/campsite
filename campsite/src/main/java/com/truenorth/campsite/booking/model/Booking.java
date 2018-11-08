package com.truenorth.campsite.booking.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.truenorth.campsite.campsite.model.Campsite;
import com.truenorth.campsite.user.model.User;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Version
	private Long version;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Campsite campsite;
	
	@ManyToOne
	private BookingStatus status;
	
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval= true)
	private List<BookedSpot> bookedSpotList;
	
	private Date bookingDate;
	private Date modificationDate;
	private String identifier;
	
	private Booking() {}
	
	public Booking(User user, Campsite campsite, String identifier, Date bookingDate, BookingStatus status) {
		this.user= user;
		this.campsite= campsite;
		this.identifier = identifier;
		this.bookingDate = bookingDate;
		this.status = status;
	}
	
	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public List<BookedSpot> getBookedSpotList() {
		return bookedSpotList;
	}

	public void setBookedSpotList(List<BookedSpot> bookedSpotList) {
		this.bookedSpotList = bookedSpotList;
	}

	
	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Campsite getCampsite() {
		return campsite;
	}

	public void setCampsite(Campsite campsite) {
		this.campsite = campsite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
