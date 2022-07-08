package api.FinalProject.starter.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.FinalProject.starter.resources.containers.BigContainer;
import api.FinalProject.starter.resources.containers.SmallContainer;
import api.FinalProject.starter.resources.items.CylindricalBox;
import api.FinalProject.starter.resources.items.Item;
import api.FinalProject.starter.resources.items.PolygonBox;
import api.FinalProject.starter.resources.items.RectangularBox;

@Service
public class OrderService {
	private List<Object> order = new ArrayList<Object>();
	private BigContainer biggie = new BigContainer(0);
	private SmallContainer smalls = new SmallContainer(0);
	
	public List<Object> getOrder(){
		return order;
	}
	
	public Object getItemByName(String itemName) {
		return (Item) order.stream().filter(t -> ((Item) t).getItemName().equals(itemName)).findFirst().get();
	}
	
	public void addItem(Object item) {
		String itemName = (String) ((LinkedHashMap)item).get("itemName");
		int amount = (Integer) ((LinkedHashMap)item).get("amount");
		double weight =(double) ((LinkedHashMap)item).get("weight");
		double x = (double) ((LinkedHashMap)item).get("x");
		double y = (double) ((LinkedHashMap)item).get("y");
		double height = (double) ((LinkedHashMap)item).get("height");
		double volume = 0;
		String baseShape = (String) ((LinkedHashMap)item).get("baseShape");
		if(baseShape.equals("circle")) {
			CylindricalBox cb = new CylindricalBox(itemName, amount, weight, x, y, height, volume, baseShape);
			volume = cb.calculateVolume(x, y, height);
			if (volume < 75.587337) {
				cb.setVolume(volume);
				order.add(cb);
			}
			else {
				order.add(null);
			}
			
		}
		else if(baseShape.equals("rectangle")){
			RectangularBox rb = new RectangularBox(itemName, amount, weight, x, y, height, volume, baseShape);
			volume = rb.calculateVolume(x, y, height);
			if (volume < 75.587337) {
				rb.setVolume(volume);
				order.add(rb);
			}
			else {
				order.add(null);
			}
		}
		else if(baseShape.equals("polygon")) {
			PolygonBox pb = new PolygonBox(itemName, amount, weight, x, y, height, volume, baseShape);
			volume = pb.calculateVolume(x, y, height);
			if (volume < 75.587337) {
				pb.setVolume(volume);
				order.add(pb);
			}
			else {
				order.add(null);
			}
		}
		else {
			order.add(null);
		}
	}
	
	public void deleteItem(String itemName) {
		int index = order.indexOf(order.stream().filter(t -> ((Item) t).getItemName().equals(itemName)).findFirst().get());
		order.remove(index);
	}
	
	public double orderVolume(List<Object> order) {
		double totalVolume = 0;
		for(Object value : order) {
			totalVolume += (((Item) value).getVolume() * ((Item) value).getAmount());
		}
		return totalVolume;
	}
	
	public double orderWeight(List<Object> order) {
		double totalWeight = 0;
		for(Object value : order) {
			totalWeight += (((Item) value).getWeight() * ((Item) value).getAmount());
		}
		return totalWeight;
	}
	
	public String bestShippingMethod() {
		double localVolume = orderVolume(order);
		double localWeight = orderWeight(order);
		double smallVolume = smalls.getContainerVolume();
		double bigVolume = biggie.getContainerVolume();
		//the counter variable stores the amount of objects from the order that have already been packed into a container
		int counter = 0;
		//when the packed items equals the items of the order, the program stops packing items
		while(counter != order.size()){
			double temp = 0;
			if(localVolume > smallVolume) {
				//every time a big container is added, the object's amount is updated
				biggie.setAmount(biggie.getAmount() + 1);
				//this loop goes through the order looking for objects that are not still in a container
				for(Object value : order) {
					int tempAmount = ((Item) value).getAmount();
					while(!(tempAmount == 0) && ((temp + ((Item) value).getVolume()) < bigVolume)) {
						temp += ((Item) value).getVolume();
						tempAmount -= 1;
						localVolume -= ((Item) value).getVolume();
						localWeight -= ((Item) value).getWeight();
					}
					if(tempAmount == 0) {
						counter += 1;
						continue;
					}
				}
			}
			else {
				//if there's the need, there will be only one small container
				smalls.setAmount(smalls.getAmount() + 1);
				smalls.setWeight(localWeight);
				//same than the loop for the big container
				for(Object value : order) {
					int tempAmount = ((Item) value).getAmount();
					while(!(tempAmount == 0) && !(((Item) value).getAmount() == 0)) {
						temp += ((Item) value).getVolume();
						tempAmount -= 1;
						localVolume -= ((Item) value).getVolume();
					}
					if(tempAmount == 0) {
						counter += 1;
						continue;
					}
				}
			}
		}
		double cost = shippingCost(biggie, smalls);
		String result = "Big containers used: " + biggie.getAmount() + '\n'
				+ "Small containers used: " + smalls.getAmount() + '\n'
				+ "Shipping cost: " + cost;
		return result;
	}

	public double shippingCost(BigContainer biggie, SmallContainer smalls) {
		return ((biggie.getAmount() * 1800) + ((smalls.getAmount()) * (smalls.getWeight() < 500 ? 1000 : 1200)));
	}
	
	
}
