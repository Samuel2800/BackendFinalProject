package api.FinalProject.starter.resources.items;

public class PolygonBox extends Item{
	public PolygonBox(String itemName, int amount, double weight, double x, double y, double height, double volume, String baseShape) {
		super(itemName, amount, weight, x, y, height, volume, baseShape);
	}

	//For a regular polygon of n sides we only need the length x of a side and the number of sides
	public double calculateArea(double x, double n) {
		return n * (x * x) / (4 * Math.tan(Math.PI / n));
	}
}