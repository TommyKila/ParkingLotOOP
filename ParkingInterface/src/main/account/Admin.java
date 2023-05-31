package main.account;

import main.enums.AccountType;

public class Admin extends Account {
	
	public Admin(String userName, String password, Employee person) {
		super(userName, password, AccountType.ADMIN, person);
	}
	
}