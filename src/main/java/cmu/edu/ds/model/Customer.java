package cmu.edu.ds.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Entity class representing a Customer in the system.
 * Maps to the "customers" table in the database.
 */
@Entity
@Table(name = "customers")
public class Customer {

    /**
     * Unique identifier for the customer.
     * Auto-generated numeric ID as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User ID of the customer, which must be a valid email address.
     */
    @NotBlank(message = "UserId is mandatory")
    @Email(message = "UserId must be a valid email address")
    private String userId;

    /**
     * Name of the customer.
     */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /**
     * Phone number of the customer.
     */
    @NotBlank(message = "Phone is mandatory")
    private String phone;

    /**
     * Primary address of the customer.
     */
    @NotBlank(message = "Address is mandatory")
    private String address;

    /**
     * Secondary address of the customer (optional).
     */
    private String address2;

    /**
     * City of the customer.
     */
    @NotBlank(message = "City is mandatory")
    private String city;

    /**
     * State of the customer, must be a valid 2-letter US state code.
     */
    @NotBlank(message = "State is mandatory")
    @Pattern(regexp = "^(AL|AK|AZ|AR|CA|CO|CT|DE|FL|GA|HI|ID|IL|IN|IA|KS|KY|LA|ME|MD|MA|MI|MN|MS|MO|MT|NE|NV|NH|NJ|NM|NY|NC|ND|OH|OK|OR|PA|RI|SC|SD|TN|TX|UT|VT|VA|WA|WV|WI|WY|DC)$",
            message = "State must be a valid 2-letter US state code")
    private String state;

    /**
     * ZIP code of the customer.
     */
    @NotBlank(message = "Zipcode is mandatory")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zipcode must be valid (12345 or 12345-6789)")
    private String zipcode;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}