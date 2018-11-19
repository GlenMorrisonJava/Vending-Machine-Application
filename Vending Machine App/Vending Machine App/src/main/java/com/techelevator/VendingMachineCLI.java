package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "Get Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_SALES_REPORT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };

	private Menu menu;
	private Inventory inventory;
	private Sales cashRegister;
	private String logtxt = "";
	private Double totalSales;

	private File log = new File("log.txt");
	private File salesReport = new File("salesReport.txt");
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	
	
	
	public void setup() throws FileNotFoundException {
		Inventory inventory = new Inventory();
		this.inventory = inventory;
		inventory.getVendingProducts();
	}

	public Inventory getInventory() {
		return inventory;
	}
	public void run() throws IOException {
		
		
		System.out.println("Main Menu");
		boolean shouldLoop = true;
		while (shouldLoop) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				inventory.getDisplay();  

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				while (shouldLoop) {
					cashRegister = inventory.getCashRegister();	
					String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						
						System.out.println("You can deposit $1, $2, $5, or $10 at a time."
								+ "\nPlease enter your deposit as a numerical value: ");
						Double startingBalance = cashRegister.getRunningTotal();
						cashRegister.feedMoney();
						saveToLog("FEED MONEY", startingBalance);
					} else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						
						Double startingBalance = cashRegister.getRunningTotal();
						inventory.getDisplay();
						System.out.println("\nPlease enter the slot of the item you would like to purchase");
						inventory.selectProduct();
						
						saveToLog(inventory.getName() + " " + inventory.getKey(), startingBalance);
					} else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						if(cashRegister.getCashBalance() == 0) {
							System.out.println("Please Feed Money Before Attempting Purchase!\n");
							run();
						}
						if(cashRegister.getCartBalance() == 0) {
							System.out.println("Please Select Items Before Attempting Purchase!\n");
							run();
						}
						Double startingBalance = cashRegister.getRunningTotal();
						cashRegister.finishTransaction();
						inventory.getSound();
						saveToLog("GIVE CHANGE", startingBalance);
						cashRegister.reset();
						run();


					}
				
				}
			}
			if (choice.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
				getSalesReport();  

			}
			
			run();
			shouldLoop = false;

		}
	}

	
	private void saveToLog(String string, Double startingAmount) throws FileNotFoundException {
		String read = "";
		Scanner reader = new Scanner(log);
		while(reader.hasNextLine()) {
			read += reader.nextLine() + "\n";
		}
		logtxt = read;
		PrintWriter writer = new PrintWriter(log);
		String timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
		String line = "";
		String format = "%-22s%-22s%s%-10s%s%-10s%n";
		line += String.format(format, timestamp, string, "$", startingAmount, "$", cashRegister.getRunningTotal());
		//line = timestamp + " " + string + " " + string2 + "	$" + startingAmount + "	$" + cashRegister.getRunningTotal() + "\n";
		logtxt += line;
		writer.print(logtxt);
		writer.flush();
		writer.close();
		 
	}
	
	public void getSalesReport() throws FileNotFoundException {
		Double totalSales = 0.0;
		String read = "";
		Scanner reader = new Scanner(salesReport);
		while(reader.hasNextLine()) {
			read += reader.nextLine() + "\n";
		}
		logtxt = read;
		PrintWriter writer = new PrintWriter(salesReport);
		String[] keys = inventory.getMapOfProducts().keySet().toArray(new String[inventory.getMapOfProducts().size()]);
		String line = "";
		for (String key : keys) {
			int sold = inventory.getMapOfProducts().get(key).getSoldCount();
			Double itemProfit = (sold * inventory.getMapOfProducts().get(key).getPrice());
			line = inventory.getMapOfProducts().get(key).getName() + "|" + sold;
			totalSales += itemProfit;
			logtxt += line + "\n";
		}
		
		String totalDollars = "\n**TOTAL SALES** $" + totalSales;
		
		logtxt += totalDollars;
		writer.print(logtxt);
		writer.flush();
		writer.close();
		 
	}
	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.setup();
		cli.run();

		// System.out.println(inventory.allVendingProducts.toString());

	}

}
