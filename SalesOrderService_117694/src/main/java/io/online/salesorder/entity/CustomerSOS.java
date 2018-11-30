package io.online.salesorder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CustomerSOS {

	public CustomerSOS() {};
	
	public CustomerSOS(Integer custId, String custFirstName, String custLastName, String custEmail) {
		super();
		this.custId = custId;
		this.custFirstName = custFirstName;
		this.custLastName = custLastName;
		this.custEmail = custEmail;
	}

	@Id
	@Column(name="cust_id")
	private Integer custId;
	
	@Column(name="cust_first_name")
	private String custFirstName;
	
	@Column(name="cust_last_name")
	private String custLastName;
	
	@Column(name="cust_email")
	private String custEmail;

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	
	
}
