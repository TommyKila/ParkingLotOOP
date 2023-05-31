package main.account;

import main.enums.AccountType;

public class Attendant extends Account {
	
	public Attendant(String userName, String password, Employee person) {
		super(userName, password, AccountType.ATTENDANT, person);
	}
	
}