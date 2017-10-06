package org.elyeva.rpa102.commons.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IFirstHourDelayEntry extends IDomainEntity {

	String getAcademicYearId();

	IFirstHourDelayEntry setAcademicYearId(String academicYearId);

	LocalDate getDate();

	IFirstHourDelayEntry setDate(LocalDate date);

	LocalTime getClassTime();

	IFirstHourDelayEntry setClassTime(LocalTime time);

	LocalTime getArrivalTime();

	IFirstHourDelayEntry setArrivalTime(LocalTime time);

	String getTeacherId();

	IFirstHourDelayEntry setTeacherId(String teacherId);

	String getAssignedTeacherId();

	IFirstHourDelayEntry setAssignedTeacherId(String teacherId);

	String getStudentId();

	IFirstHourDelayEntry setStudentId(String studentId);
}
