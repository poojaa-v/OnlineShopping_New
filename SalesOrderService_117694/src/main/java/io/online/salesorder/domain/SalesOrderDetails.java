package io.online.salesorder.domain;

import java.util.ArrayList;
import java.util.List;

public class SalesOrderDetails {
	private String orderDesc;
	private String orderDate;
	private Integer custId;	
	private List<Item> itemNameList = new ArrayList<Item>();
	private String errVar;

	public SalesOrderDetails() {}
	
	public SalesOrderDetails(String orderDesc, String orderDate, Integer custId, List<Item> itemNameList) {
		super();
		this.orderDesc = orderDesc;
		this.orderDate = orderDate;
		this.custId = custId;
		this.itemNameList = itemNameList;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public List<Item> getItemNameList() {
		return itemNameList;
	}

	public void setItemNameList(List<Item> itemNameList) {
		this.itemNameList = itemNameList;
	}

	public String getErrVar() {
		return errVar;
	}

	public void setErrVar(String errVar) {
		this.errVar = errVar;
	};
	
				
}
