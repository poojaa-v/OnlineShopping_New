package io.online.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.online.item.domain.Item;
import io.online.item.repository.ItemDetailsRepository;

@RestController
public class ItemDetailsController {

	@Autowired
	private ItemDetailsRepository itemDetailsRepository;
	
	@GetMapping("/items")
	public List<Item> getItemDetails() {
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<Inside getItemDetails Controller>>>>>>>>>>>>>>>>>>>>>");
		return itemDetailsRepository.getItemDetails();	        
	}
	
	 @GetMapping("/items/{itemName}")
	    public Item item(@PathVariable("itemName") String itemName) {
	        return itemDetailsRepository.itemByName(itemName);
	    }
	
}
