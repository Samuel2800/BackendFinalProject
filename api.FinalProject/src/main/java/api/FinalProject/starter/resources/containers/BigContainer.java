package api.FinalProject.starter.resources.containers;

public class BigContainer {
	//the volume of the container stays fixed
	private double containerVolume;
	private int amount;

	public BigContainer(int amount) {
		this.containerVolume = 75.587337;
		this.amount = amount;
	}

	public double getContainerVolume() {
		return containerVolume;
	}

	public void setContainerVolume(double containerVolume) {
		this.containerVolume = containerVolume;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}