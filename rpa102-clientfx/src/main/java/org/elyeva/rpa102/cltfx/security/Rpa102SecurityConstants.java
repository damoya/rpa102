package org.elyeva.rpa102.cltfx.security;

import es.indra.eplatform.permissions.PermissionDefinition;

public final class Rpa102SecurityConstants {

	public static final PermissionDefinition FIRST_HOUR_DELAY_NEW_ENTRY =
			new PermissionDefinition("FirstHourDelay.NewEntry");

	public static final PermissionDefinition FIRST_HOUR_DELAY_QUERY_WKS =
			new PermissionDefinition("FirstHourDelay.queryWks");

	public static final PermissionDefinition CLASSROOM_INCIDENCE_NEW_ENTRY =
			new PermissionDefinition("ClassroomIncidence.NewEntry");

	public static final PermissionDefinition CLASSROOM_INCIDENCE_QUERY_WKS =
			new PermissionDefinition("ClassroomIncidence.queryWks");

	public static final PermissionDefinition BREAK_TIME_STAYS_NEW_ENTRY =
			new PermissionDefinition("BreakTimeStays.NewEntry");

	public static final PermissionDefinition BREAK_TIME_STAYS_QUERY_WKS =
			new PermissionDefinition("BreakTimeStays.queryWks");

	public static final PermissionDefinition SMS_SEND =
			new PermissionDefinition("SMS.send");

	static PermissionDefinition[] permissions = {
		FIRST_HOUR_DELAY_NEW_ENTRY,
		FIRST_HOUR_DELAY_QUERY_WKS,
		CLASSROOM_INCIDENCE_NEW_ENTRY,
		CLASSROOM_INCIDENCE_QUERY_WKS,
		BREAK_TIME_STAYS_NEW_ENTRY,
		BREAK_TIME_STAYS_QUERY_WKS,
		SMS_SEND
	};

	/**
	 * Utility class constructor.
	 */
	private Rpa102SecurityConstants() {
		// empty method
	}
}
