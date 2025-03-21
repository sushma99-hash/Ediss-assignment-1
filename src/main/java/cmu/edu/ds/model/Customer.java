package cmu.edu.ds.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId") // This explicitly tells JPA to use "userId" in the database
    private String userId;

    @Column(name = "name") // Explicitly map to the "name" column in the database
    private String name;

    @Column(name = "phone") // Explicitly map to the "phone" column in the database
    private String phone;

    @Column(name = "address") // Explicitly map to the "address" column in the database
    private String address;

    @Column(name = "address2") // Explicitly map to the "address2" column in the database
    private String address2;

    @Column(name = "city") // Explicitly map to the "city" column in the database
    private String city;

    @Column(name = "state") // Explicitly map to the "state" column in the database
    private String state;

    @Column(name = "zipcode") // Explicitly map to the "zipcode" column in the database
    private String zipcode;
}