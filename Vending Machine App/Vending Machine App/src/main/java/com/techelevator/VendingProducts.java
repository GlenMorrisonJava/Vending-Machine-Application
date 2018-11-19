package com.techelevator;

  

public class VendingProducts {
	private String location;
	private String name;
	private String stringPrice;
	private Double price;
	private int soldCount;
	private int availableCount = 5;
	private String save;
	

	public VendingProducts(String location, String name, String stringPrice, int availableCount, int soldCount) {
		this.location = location;
		this.name = name;
		this.stringPrice = stringPrice;
		this.price = Double.parseDouble(stringPrice);
		this.availableCount = availableCount;
		this.soldCount = soldCount;
	}
	public boolean isSoldOut() {
		if(availableCount == 0) {
			return true;
		}
		else return false;
	} 

	public void setSoldCount() {
		soldCount++;
		availableCount--;
	}
	public int getAvailableCount() {
		return this.availableCount;  
	}  

	
	
	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}  

	public Double getPrice() {
		return price;
	}
  
	public String getStringPrice() {
		return stringPrice;
	}  

	public String toDisplay() {
		String save = "";
		String format = "%-8s%-20s%s%-10s%-9s%n";
		save += String.format(format, location, name, "$", price, availableCount);
		return save;
		
		
		
	}
	public int getSoldCount() {
	
		return soldCount;
	}
}
