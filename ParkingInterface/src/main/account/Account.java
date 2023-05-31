package main.account;

import main.enums.AccountType;

public abstract class Account {
	private String userName;
	private String password;
	private AccountType type;
	private Employee person;
	
	public Account() {
	}

	public Account(String userName, String password, AccountType type, Employee person) {
		this.userName = userName;
		this.password = password;
		this.type = type;
		this.person = person;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getId() {
		return person.getId();
	}
	
	public String getFirstName() {
		return person.getFirstName();
	}
	
	public String getLastName() {
		return person.getLastName();
	}
	
	public String getEmail() {
		return person.getEmail();
	}

	public String getPhoneNum() {
		return person.getPhoneNum();
	}
}
