package com.techelevator;

import java.util.Map;
import java.util.Scanner;

public class Sales {

	private Double customerCartBalance = 0.0;
	private Double customerCashBalance = 0.0;
	private Double endingBalance = 0.0;
	private Double printEndingBalance = 0.0;


	public Double getPrintEndingBalance() {
		return printEndingBalance;
	}

	public Sales() {

	}

	public void addItemToCart(Double itemPrice) {
		customerCartBalance += itemPrice;
	}

	public Double feedMoney() {

		Scanner input = new Scanner(System.in);
		String stringDeposit = input.nextLine();
		Double deposit = Double.parseDouble(stringDeposit);
		if (deposit != 1.0 && deposit != 2.0 && deposit != 5.0 && deposit != 10.0) {
			System.out.println("\nPlease deposit only $1, $2, $5, or $10 at a time.\n"
					+ "Enter your deposit as a numerical value: ");
			deposit = 0.0;
			feedMoney();
		} else {
			if (customerCashBalance + deposit >= 20) {
				System.out.println("Cash balances can not exceed $20.");
				System.out.println("Your balance is " + customerCashBalance + ".");
				System.out.println("Please do not exceed $20 after deposit.\nEnter your deposit: ");
				deposit = 0.0;
				feedMoney();

			} else {
				customerCashBalance += deposit;
				System.out.println("Deposit of $" + deposit + " was succesful.");
				System.out.println("Your balance is now $ " + customerCashBalance + ".\n");

			}

		}
		return deposit;

	}

	public Double getCartBalance() {
		return customerCartBalance;
	}

	public Double getCashBalance() {

		return this.customerCashBalance;
	}
	public Double getRunningTotal() {
		return customerCashBalance - customerCartBalance;
	}
	public void finishTransaction() {
		
		if (customerCashBalance >= customerCartBalance) {
			printEndingBalance += customerCashBalance - customerCartBalance;
			endingBalance = printEndingBalance;
			System.out.println("Your Change Is: $" + endingBalance + "\n" + getChange());
		} else {
			System.out.println("Insufficient Funds! Please Feed More Money");
		}

	}

	private String getChange() {
		Integer quarterCount = 0;  
		Integer dimeCount = 0;
		Integer nickelCount = 0;

		while (endingBalance >= .25) {
			quarterCount++;
			endingBalance -= 0.25;
		}
		while (endingBalance >= .1) {
			dimeCount++;
			endingBalance -= .1;
		}
		while (endingBalance >= .05) {
			nickelCount++;
			endingBalance -= .05;
		}
		customerCartBalance = 0.0;
		customerCashBalance = 0.0;
		return "Quarters: " + quarterCount + "\nDimes: " + dimeCount + "\nNickels: " + nickelCount + "\n";
	}
	
	public void reset() {
		printEndingBalance = 0.0;
	}

}