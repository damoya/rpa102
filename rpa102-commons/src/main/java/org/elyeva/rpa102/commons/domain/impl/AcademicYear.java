package org.elyeva.rpa102.commons.domain.impl;

import org.elyeva.rpa102.commons.domain.IAcademicYear;

public class AcademicYear extends ADomainEntity implements IAcademicYear {

	/** Default Serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String description;

	public AcademicYear() {

	}

	public AcademicYear(String id) {
		setId(id);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public IAcademicYear setDescription(String description) {
		this.description = description;

		return this;
	}

}
