package api.FinalProject.starter.resources.items;

public class RectangularBox extends Item{
	public RectangularBox(String itemName, int amount, double weight, double x, double y, double height, double volume, String baseShape) {
		super(itemName, amount, weight, x, y, height, volume, baseShape);
	}

	public double calculateArea(double x, double y) {
		return x * y;
	}
}