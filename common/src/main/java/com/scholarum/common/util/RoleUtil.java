package com.scholarum.common.util;

public class RoleUtil {

	public static final String ALL_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOLARUM_ADVISOR')"
			+ " or hasAuthority('ROLE_SCHOOL') or hasAuthority('ROLE_SCHOOL_FINANCE') or hasAuthority('ROLE_SCHOOL_OPS')";

	public static final String ALL_ADMIN_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOOL')";

	public static final String ALL_FINANCE_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOOL') or hasAuthority('ROLE_SCHOOL_FINANCE')";

	public static final String ALL_OPS_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOOL') or hasAuthority('ROLE_SCHOOL_OPS')";

	public static final String SCHOLARUM_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOLARUM_ADVISOR')";

	public static final String SCHOOL_AUTH = "hasAuthority('ROLE_SCHOOL') or hasAuthority('ROLE_SCHOOL_FINANCE')"
			+ " or hasAuthority('ROLE_SCHOOL_OPS')";

	public static final String SCHOLARUM_ADMIN_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')";

	public static final String SCHOOL_ADMIN_AUTH = "hasAuthority('ROLE_SCHOOL')";

	public static final String SCHOLARUM_FINANCE_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE')";

	public static final String SCHOOL_FINANCE_AUTH = "hasAuthority('ROLE_SCHOOL') or hasAuthority('ROLE_SCHOOL_FINANCE')";

	public static final String SCHOLARUM_OPS_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_OPS')";

	public static final String SCHOOL_OPS_AUTH = "hasAuthority('ROLE_SCHOOL') or hasAuthority('ROLE_SCHOOL_OPS')";

}
