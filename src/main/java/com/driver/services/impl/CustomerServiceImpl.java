package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = new Customer();
		customer = customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> drivers = driverRepository2.findAll();

		drivers.sort(Comparator.comparingInt(Driver::getDriverId));

		Driver assignedDriver=null;

		for (Driver driver : drivers) {
			if (driver.getCab().isAvailable()) {
				assignedDriver = driver;
				break;
			}
		}

		if (assignedDriver == null)
			throw new Exception("No cab available!");

		
		TripBooking tripBooking = new TripBooking(toLocation, fromLocation, distanceInKm, TripStatus.CONFIRMED);
		

		assignedDriver.getCab().setAvailable(false);
		tripBooking.setBill(assignedDriver.getCab().getPerKmRate()*distanceInKm);
		Customer customer=customerRepository2.findById(customerId).get();

		tripBooking.setDriver(assignedDriver);
		tripBooking.setCustomer(customer);

		if(Objects.isNull(assignedDriver.getTripBookingList())) {
			assignedDriver.setTripBookingList(new ArrayList<>());
		}
		assignedDriver.getTripBookingList().add(tripBooking);
		if (Objects.isNull(customer.getTripBookingList())) {
			customer.setTripBookingList(new ArrayList<>());
		}
		customer.getTripBookingList().add(tripBooking);
		customerRepository2.save(customer);
		driverRepository2.save(assignedDriver);
		tripBookingRepository2.save(tripBooking);

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setTripStatus(String.valueOf(TripStatus.CANCELED));
		tripBooking.setBill(0);
		Driver driver = tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();
		tripBooking.setTripStatus(String.valueOf(TripStatus.COMPLETED));
		Driver driver = tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);


	}
}
