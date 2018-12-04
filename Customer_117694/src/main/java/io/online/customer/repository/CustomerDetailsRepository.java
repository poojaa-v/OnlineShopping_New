package io.online.customer.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.online.customer.domain.Customer;

@Repository
public class CustomerDetailsRepository {

	private final String GET_ALL_CUST_QUERY = "select * from customer_117694";
	private final String SQL_INSERT = "insert into customer_117694(id,email,first_name,last_name) values(?,?,?,?)";

	private final JdbcTemplate jdbcTemplate;
	Logger logger = LoggerFactory.getLogger(CustomerDetailsRepository.class);	

	@Autowired
	public CustomerDetailsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private final RowMapper<Customer> rowMapper = (ResultSet rs, int row) -> {
		Customer customer = new Customer();
		customer.setId(rs.getString("id"));
		customer.setEmail(rs.getString("email"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
		return customer;
	};

	public List<Customer> getCustomerDetails() {
		List <Customer> customerList = null;

		try {
			logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<Inside getCustomerDetails Repository>>>>>>>>>>>>>>>>>>>>>");
			customerList = new ArrayList<Customer>(); 
			customerList = 	jdbcTemplate.query(GET_ALL_CUST_QUERY, rowMapper);
			logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<customerList>>>>>>>>>>>>>>>>>>>>>" + customerList);

		} catch (Exception e){
			e.printStackTrace();
		}

		return customerList;
	}
	
	public Customer saveCustDetails(Customer cust) {
		try {
			logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<Inside saveCustDetails Repository>>>>>>>>>>>>>>>>>>>>>");

			if (cust != null && cust.getId() != null ) {
				this.jdbcTemplate.update(connection -> {
		            PreparedStatement ps = connection.prepareStatement(SQL_INSERT);
					ps.setString(1, cust.getId());
		            ps.setString(2, cust.getEmail());
		            ps.setString(3, cust.getFirstName());
		            ps.setString(4, cust.getLastName());
		            return ps;
		        });
			}
						
			logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<After Save>>>>>>>>>>>>>>>>>>>>>");

		} catch (Exception e){
			e.printStackTrace();
		}

		return cust;
	}
	 

}
