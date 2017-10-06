package org.elyeva.rpa102.commons.domain.impl;


import org.elyeva.rpa102.commons.domain.IDomainEntity;

public class ADomainEntity implements IDomainEntity {

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

	public String id;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}