package org.elyeva.rpa102.commons.domain.impl;

import org.elyeva.rpa102.commons.domain.IPhoneNumber;

public class PhoneNumber implements IPhoneNumber {

	private String phoneNumber;

	private String description;

	public PhoneNumber(String phoneNumber, String description) {
		this.phoneNumber = phoneNumber;
		this.description = description;
	}

	@Override
	public String getNumber() {
		return phoneNumber;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
