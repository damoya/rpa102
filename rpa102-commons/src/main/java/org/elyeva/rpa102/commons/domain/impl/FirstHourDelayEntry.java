package org.elyeva.rpa102.commons.domain.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.elyeva.rpa102.commons.domain.IFirstHourDelayEntry;

public class FirstHourDelayEntry extends ADomainEntity implements IFirstHourDelayEntry {

	/** Default Serial Version UID. */
	private static final long serialVersionUID = 1L;

	private String academicYearId;

	private LocalDate date;

	private LocalTime classTime;

	private LocalTime arrivalTime;

	private String teacherId;

	private String assignedTeacherId;

	private String studentId;

	/**
	 * Default Constructor.
	 */
	public FirstHourDelayEntry() {
	}

	public FirstHourDelayEntry(IFirstHourDelayEntry entity) {
		setId(entity.getId());
		setAcademicYearId(entity.getAcademicYearId());
		setDate(entity.getDate());
		setClassTime(entity.getClassTime());
		setTeacherId(entity.getTeacherId());
		setAssignedTeacherId(entity.getAssignedTeacherId());
		setStudentId(entity.getStudentId());
	}

	@Override
	public String getAcademicYearId() {
		return academicYearId;
	}

	@Override
	public IFirstHourDelayEntry setAcademicYearId(String academicYearId) {
		this.academicYearId = academicYearId;

		return this;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public IFirstHourDelayEntry setDate(LocalDate date) {
		this.date = date;

		return this;
	}


	@Override
	public LocalTime getClassTime() {
		return classTime;
	}

	@Override
	public IFirstHourDelayEntry setClassTime(LocalTime time) {
		this.classTime = time;

		return this;
	}

	@Override
	public String getTeacherId() {
		return teacherId;
	}

	@Override
	public IFirstHourDelayEntry setTeacherId(String teacherId) {
		this.teacherId = teacherId;

		return this;
	}

		@Override
	public String getAssignedTeacherId() {
		return assignedTeacherId;
	}

	@Override
	public IFirstHourDelayEntry setAssignedTeacherId(String teacherId) {
		this.assignedTeacherId = teacherId;

		return this;
	}

	@Override
	public String getStudentId() {
		return studentId;
	}

	@Override
	public IFirstHourDelayEntry setStudentId(String studentId) {
		this.studentId = studentId;

		return this;
	}

	@Override
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	@Override
	public IFirstHourDelayEntry setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;

		return this;
	}
}
