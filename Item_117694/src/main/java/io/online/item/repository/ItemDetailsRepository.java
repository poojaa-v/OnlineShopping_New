package io.online.item.repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.online.item.domain.Item;

@Repository
public class ItemDetailsRepository {

	private final String GET_ALL_ITEMS_QUERY = "select * from item_117694";
	private final String SQL_QUERY_BY_NAME = "select * from item_117694 where name = ?";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ItemDetailsRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private final RowMapper<Item> rowMapper = (ResultSet rs, int row) -> {
		
		Item item = new Item();
		item.setItemId(rs.getString("id"));
		item.setItemName(rs.getString("name"));
		item.setItemDesc(rs.getString("description"));
		item.setItemPrice(rs.getDouble("price"));
		
		return item;
	};
	
	public List<Item> getItemDetails() {
		List <Item> itemsList = null;

		try {
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<Inside getCustomerDetails Repository>>>>>>>>>>>>>>>>>>>>>");
			itemsList = new ArrayList<Item>(); 
			itemsList = jdbcTemplate.query(GET_ALL_ITEMS_QUERY, rowMapper);
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<itemsList>>>>>>>>>>>>>>>>>>>>>" + itemsList);

		} catch (Exception e){
			e.printStackTrace();
		}

		return itemsList;
	}

	public Item itemByName(String itemName) {
		List <Item> itemsList = null;
		try {
			System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<Inside getCustomerDetails Repository itemByName>>>>>>>>>>>>>>>>>>>>> + itemName");
			itemsList = new ArrayList<Item>(); 
			//return this.jdbcTemplate.queryForObject(SQL_QUERY_BY_NAME, new Object[]{itemName}, rowMapper);
			itemsList = jdbcTemplate.query(SQL_QUERY_BY_NAME, rowMapper, new Object[]{itemName});
			if(itemsList != null  && itemsList.size()>0){
				return itemsList.get(0);
			} else {
				return new Item();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return new Item();
	}

}
