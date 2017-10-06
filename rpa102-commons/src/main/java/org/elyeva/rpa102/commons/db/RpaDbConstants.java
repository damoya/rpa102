package org.elyeva.rpa102.commons.db;

public final class RpaDbConstants {

	public static class TblConfig {
		public static final String NAME = "CONFIG";

		public static final String COL_PARAM_ID = "PARAM_ID";
		public static final String COL_VALUE = "VALUE";
		public static final String COL_PARAM_NAME = "PARAM_NAME";

		/** Constants class constructor. */
		private TblConfig() {
			// empty method
		}
	}

	public static class TblFirstHourDelay {
		public static final String NAME = "FIRST_HOUR_DELAY_ENTRIES";

		public static final String COL_STUDENT_ID = "STUDENT_ID";
		public static final String VCOL_SMS_NOTIFICATION = "SMS_NOTIFICATION";

		/** Constants class constructor. */
		private TblFirstHourDelay() {
			// empty method
		}
	}

	public static class TblClassroomIncidences {
		public static final String NAME = "CLASSROOM_INCIDENCES";

		public static final String COL_STUDENT_ID = "STUDENT_ID";

		public static final String COL_SCHOOL_MGMT_ORDER_CAUSE = "SCHOOL_MGMT_ORDER_CAUSE";
		public static final String COL_USE_OF_MOBILE_CAUSE = "USE_OF_MOBILE_CAUSE";
		public static final String COL_BREAK_MATERIAL_CAUSE = "BREAK_MATERIAL_CAUSE";
		public static final String COL_LACK_OF_RESPECT_CAUSE = "LACK_OF_RESPECT_CAUSE";
		public static final String COL_GO_OUT_FROM_SCHOOL_CAUSE = "GO_OUT_FROM_SCHOOL_CAUSE";
		public static final String COL_DISTURB_CAUSE = "DISTURB_CAUSE";
		public static final String COL_BREACH_OF_ORDER_CAUSE = "BREACH_OF_ORDER_CAUSE";
		public static final String COL_INTERRUPT_TEACHER_EXPLANATION_CAUSE = "INTERRUPT_TEACHER_EXPLANATION_CAUSE";
		public static final String COL_TO_NOT_WORK_CAUSE = "TO_NOT_WORK_CAUSE";
		public static final String COL_SMOKE_CAUSE = "SMOKE_CAUSE";
		public static final String COL_HIT_OTHER_STUDENT_CAUSE = "HIT_OTHER_STUDENT_CAUSE";
		public static final String COL_OTHER_CAUSE = "OTHER_CAUSE";

		public static final String VCOL_SMS_NOTIFICATION = "SMS_NOTIFICATION";

		public static final String [] CAUSES = {
				TblClassroomIncidences.COL_SCHOOL_MGMT_ORDER_CAUSE,
				TblClassroomIncidences.COL_USE_OF_MOBILE_CAUSE,
				TblClassroomIncidences.COL_BREAK_MATERIAL_CAUSE,
				TblClassroomIncidences.COL_LACK_OF_RESPECT_CAUSE,
				TblClassroomIncidences.COL_GO_OUT_FROM_SCHOOL_CAUSE,
				TblClassroomIncidences.COL_DISTURB_CAUSE,
				TblClassroomIncidences.COL_BREACH_OF_ORDER_CAUSE,
				TblClassroomIncidences.COL_INTERRUPT_TEACHER_EXPLANATION_CAUSE,
				TblClassroomIncidences.COL_TO_NOT_WORK_CAUSE,
				TblClassroomIncidences.COL_SMOKE_CAUSE,
				TblClassroomIncidences.COL_HIT_OTHER_STUDENT_CAUSE,
				TblClassroomIncidences.COL_OTHER_CAUSE
		};

		/** Constants class constructor. */
		private TblClassroomIncidences() {
			// empty method
		}
	}

	public static class TblBreakTimeStays {
		public static final String NAME = "BREAK_TIME_STAYS";

		public static final String COL_STUDENT_ID = "STUDENT_ID";

		public static final String COL_FIRST_HOUR_DELAY_CAUSE = "FIRST_HOUR_DELAY_CAUSE";
		public static final String COL_SCHOOL_MGMT_ORDER_CAUSE = "SCHOOL_MGMT_ORDER_CAUSE";

		public static final String VCOL_SMS_NOTIFICATION = "SMS_NOTIFICATION";

		public static final String [] CAUSES = {
				TblBreakTimeStays.COL_FIRST_HOUR_DELAY_CAUSE,
				TblBreakTimeStays.COL_SCHOOL_MGMT_ORDER_CAUSE
		};

		/** Constants class constructor. */
		private TblBreakTimeStays() {
			// empty method
		}
	}


	// -- Security entities

	public static class TblPermissions {
		public static final String NAME = "PERMISSIONS";

		public static final String COL_ID = "PERMISSION_ID";
		public static final String COL_DESCRIPTION = "DESCRIPTION";

		/** Constants class constructor. */
		private TblPermissions() {
			// empty method
		}
	}

	public static class TblUsers {
		public static final String NAME = "USERS";

		public static final String COL_USER_ID = "USER_ID";
		public static final String COL_NAME = "NAME";
		public static final String COL_PASSWORD = "PASSWORD";
		public static final String COL_TEACHER_ID = "TEACHER_ID";
		public static final String COL_PHONE_1 = "PHONE_1";
		public static final String COL_PHONE_2 = "PHONE_2";

		/** Constants class constructor. */
		private TblUsers() {
			// empty method
		}
	}

	public static class TblRolePermissions {
		public static final String NAME = "ROLE_PERMISSION";

		public static final String COL_ROLE_ID = "ROLE_ID";
		public static final String COL_PERMISSION_ID = "PERMISSION_ID";

		/** Constants class constructor. */
		private TblRolePermissions() {
			// empty method
		}
	}

	public static class TblUserRole {
		public static final String NAME = "USER_ROLE";

		public static final String COL_USER_ID = "USER_ID";
		public static final String COL_ROLE_ID = "ROLE_ID";

		/** Constants class constructor. */
		private TblUserRole() {
			// empty method
		}
	}

	// Sms Management

	public static class TblSms {
		public static final String NAME = "SMS";

		public static final String COL_MSG_ID = "MSG_ID";
		public static final String COL_MSG_TEXT = "MSG_TEXT";
		public static final String COL_FROM_TEXT = "FROM_TEXT";
		public static final String COL_DEST_PHONE = "DEST_PHONE";
		public static final String COL_SENT_STATE = "SENT_STATE";
		public static final String COL_REFERENCED_ELEMENT = "REFERENCED_ELEMENT";

		/** Constants class constructor. */
		private TblSms() {
			// empty method
		}
	}

	public static class TblStudentsByAcademicYear {
		public static final String NAME = "STUDENTS_BY_ACADEMIC_YEAR";

		public static final String COL_ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
		public static final String COL_COURSE_ID = "COURSE_ID";
		public static final String COL_STUDENT_ID = "STUDENT_ID";

		/** Constants class constructor. */
		private TblStudentsByAcademicYear() {
			// empty method
		}
	}
}
