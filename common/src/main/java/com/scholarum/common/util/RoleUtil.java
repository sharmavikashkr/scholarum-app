package com.scholarum.common.util;

public class RoleUtil {

	public static final String ALL_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOLARUM_ADVISOR')"
			+ " or hasAuthority('ROLE_MERCHANT') or hasAuthority('ROLE_MERCHANT_FINANCE') or hasAuthority('ROLE_MERCHANT_OPS')";

	public static final String ALL_ADMIN_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_MERCHANT')";

	public static final String ALL_FINANCE_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_MERCHANT') or hasAuthority('ROLE_MERCHANT_FINANCE')";

	public static final String ALL_OPS_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_MERCHANT') or hasAuthority('ROLE_MERCHANT_OPS')";

	public static final String SCHOLARUM_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE') or hasAuthority('ROLE_SCHOLARUM_OPS') or hasAuthority('ROLE_SCHOLARUM_ADVISOR')";

	public static final String MERCHANT_AUTH = "hasAuthority('ROLE_MERCHANT') or hasAuthority('ROLE_MERCHANT_FINANCE')"
			+ " or hasAuthority('ROLE_MERCHANT_OPS')";

	public static final String SCHOLARUM_ADMIN_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')";

	public static final String MERCHANT_ADMIN_AUTH = "hasAuthority('ROLE_MERCHANT')";

	public static final String SCHOLARUM_FINANCE_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_FINANCE')";

	public static final String MERCHANT_FINANCE_AUTH = "hasAuthority('ROLE_MERCHANT') or hasAuthority('ROLE_MERCHANT_FINANCE')";

	public static final String SCHOLARUM_OPS_AUTH = "hasAuthority('ROLE_SCHOLARUM') or hasAuthority('ROLE_SCHOLARUM_SUPERVISOR')"
			+ " or hasAuthority('ROLE_SCHOLARUM_OPS')";

	public static final String MERCHANT_OPS_AUTH = "hasAuthority('ROLE_MERCHANT') or hasAuthority('ROLE_MERCHANT_OPS')";

}
