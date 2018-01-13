package com.scholarum.common.util;

public class RoleUtil {

	public static final String ALL_AUTH = "hasAuthority('ROLE_SCHOLARUM_ADMIN') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOLARUM_ADVISOR')"
			+ " or hasAuthority('ROLE_SCHOOL_ADMIN') or hasAuthority('ROLE_SCHOOL_FINANCE') or hasAuthority('ROLE_SCHOOL_OPS')"
			+ " or hasAuthority('ROLE_SCHOOL_TEACHER') or hasAuthority('ROLE_SCHOOL_STAFF')";

	public static final String ALL_ADMIN_AUTH = "hasAuthority('ROLE_SCHOLARUM_ADMIN') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOOL_ADMIN') or hasAuthority('ROLE_INSTITUTION_ADMIN')";

	public static final String SCHOLARUM_AUTH = "hasAuthority('ROLE_SCHOLARUM_ADMIN') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOLARUM_ADVISOR')";

	public static final String INSTITUTION_AUTH = "hasAuthority('ROLE_INSTITUTION_ADMIN') or hasAuthority('ROLE_INSTITUTION_SUPERVISOR')"
			+ " or hasAuthority('ROLE_INSTITUTION_FINANCE') or hasAuthority('ROLE_INSTITUTION_OPS')";

	public static final String SCHOOL_AUTH = "hasAuthority('ROLE_SCHOOL_ADMIN') or hasAuthority('ROLE_SCHOOL_FINANCE')"
			+ " or hasAuthority('ROLE_SCHOOL_OPS') or hasAuthority('ROLE_SCHOOL_TEACHER') or hasAuthority('ROLE_SCHOOL_STAFF')";

	public static final String SCHOLARUM_ADMIN_AUTH = "hasAuthority('ROLE_SCHOLARUM_ADMIN') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')";

	public static final String SCHOOL_ADMIN_AUTH = "hasAuthority('ROLE_SCHOOL_ADMIN')";

}
