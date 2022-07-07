package api.FinalProject.starter.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import api.FinalProject.starter.resources.items.Item;
import api.FinalProject.starter.service.OrderService;

public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@RequestMapping("/products")
	public List<Item> getOrder(){
		return orderService.getOrder();
	}
	
	@RequestMapping("/products/{name}")
	public Item getItem(@PathVariable String name) {
		return orderService.getItemByName(name);
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/products")
	public void addOneItem(@RequestBody Item item) {
		orderService.addItem(item);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value = "/products/{name}")
	public void deleteOneItem(@PathVariable String name) {
		orderService.deleteItem(name);
	}
	
	@RequestMapping("/shipping")
	public String shippingMethod() {
		return orderService.bestShippingMethod();
	}
}
