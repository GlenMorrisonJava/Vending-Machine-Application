package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Inventory {
	private static Map<String, VendingProducts> mapOfProducts = new TreeMap<String, VendingProducts>();
	//private String display = "";
	private static Sales cashRegister = new Sales();
	private String sounds = "";
	private String key;
	private String name;
	private Double price;
	
	public void getVendingProducts() throws FileNotFoundException {
		String pretty = "";
		File currentInventory = new File(
				"vendingmachine.txt");
		Scanner getProducts = new Scanner(currentInventory);
		while (getProducts.hasNextLine()) {
			String line = getProducts.nextLine();
			String[] productValues = line.split("\\|");
			int availableCount = 5;
			int soldCount = 0;
			VendingProducts product = new VendingProducts(productValues[0], productValues[1], productValues[2],
					availableCount, soldCount);

			// display += product.toDisplay();

			mapOfProducts.put(productValues[0], product);

		}
		getProducts.close();

	}

	public void selectProduct() {
		String selection = null;
		try{Scanner selectProduct = new Scanner(System.in);
		selection = selectProduct.nextLine();
		selection = selection.toUpperCase();
		key = selection;
		name = mapOfProducts.get(key).getName();
		price = mapOfProducts.get(key).getPrice();
		
		}catch(Exception e) {
		}
		if (selection == null){
			System.out.println("Please enter a slot value.\nie: A1");
			selectProduct();
		}
		//WORK ON EXCEPTION
		int amountLeft = mapOfProducts.get(selection).getAvailableCount();
		if (amountLeft == 0) { 
			System.out.println("\nItem Sold Out!\nPlease select a different item.");
			selectProduct();
		} else {
			cashRegister.addItemToCart(mapOfProducts.get(selection).getPrice());
			System.out.println("Shopping cart balance: $" + cashRegister.getCartBalance());
			mapOfProducts.get(selection).setSoldCount();
			System.out.println("Total Items Sold: " + mapOfProducts.get(selection).getSoldCount());
			updateMap(selection);
			
			storeSound(selection);
		}
		
	}
	public void saveToLog(String name, String type, Double amount) {
		
		
		
		
		//		Timestamp currentTime = new Timestamp(time);
	}

	private void storeSound(String selection) {

		if(selection.contains("A")) {
			sounds += "CRUNCH CRUNCH, YUM!\n";
		}
		if(selection.contains("B")) {
			sounds += "MUNCH MUNCH, YUM!\n";
		}
		if(selection.contains("C")) {
			sounds += "GLUG GLUG, YUM!\n";
		}
		if(selection.contains("D")) {
			sounds += "CHEW CHEW, YUM!\n";
		}
	}
	
	public void getSound() {
		System.out.println(sounds);
	}

	private void updateMap(String selection) {
		String location = mapOfProducts.get(selection).getLocation();
		String name = mapOfProducts.get(selection).getName();
		String stringPrice = mapOfProducts.get(selection).getStringPrice();
		int availableCount = mapOfProducts.get(selection).getAvailableCount();
		int soldCount = mapOfProducts.get(selection).getSoldCount();

		VendingProducts product = new VendingProducts(location, name, stringPrice, availableCount, soldCount);
		mapOfProducts.put(location, product);
	}

	public static Map<String, VendingProducts> getMapOfProducts() {
		return mapOfProducts;
	}

	public void getDisplay() {
		String[] keys = mapOfProducts.keySet().toArray(new String[mapOfProducts.size()]);
		String newDisplay = "";
		for (String key : keys) {
			newDisplay += mapOfProducts.get(key).toDisplay();
		}

		String format = "%-8s%-20s%-11s%-9s%n";
		System.out.println();
		System.out.print("-----------------Items For Sale------------------\n");
		System.out.printf(format, "Slot", "Name", "Price", "Remaining");
		System.out.println("-------------------------------------------------");
		System.out.println(newDisplay);
		System.out.println();
	}

	public Sales getCashRegister() {

		return cashRegister;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	

}
