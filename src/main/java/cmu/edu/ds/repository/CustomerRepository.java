package cmu.edu.ds.repository;

import cmu.edu.ds.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor for dependency injection of JdbcTemplate.
     * @param jdbcTemplate The JDBC template to be used for database operations
     */
    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addCustomer(Customer customer) {

        return jdbcTemplate.update("INSERT INTO customers (userId, name, phone, address, address2, city, state, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                customer.getUserId(), customer.getName(), customer.getPhone(),
                customer.getAddress(), customer.getAddress2(), customer.getCity(),
                customer.getState(), customer.getZipcode());
    }

    public Optional<Customer> getCustomerById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id=?", customerRowMapper, id));
    }

    /**
     * Retrieves a customer from the database by their userId (likely a username or external ID).
     * Explicitly handles EmptyResultDataAccessException and returns an empty Optional.
     *
     * @param userId The userId of the customer to retrieve
     * @return Optional containing the Customer if found, empty Optional if no match
     */
    public Optional<Customer> getCustomerByUserId(String userId) {
        String sql = "SELECT * FROM customers WHERE userId = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, customerRowMapper, userId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // Return empty Optional if no result found
        }
    }

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> new Customer(
            rs.getLong("id"),
            rs.getString("userId"), rs.getString("name"),
            rs.getString("phone"), rs.getString("address"), rs.getString("address2"),
            rs.getString("city"), rs.getString("state"), rs.getString("zipcode")
    );
}