package com.scholarum.common.util;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.jetty.http.HttpStatus;

import com.scholarum.common.exception.ScException;

public class ScholarumUtil {

	public static void vaidateRequest(Object request) {
		if (CommonUtil.isNull(request)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Mandatory params missing");
		}
	}

	public static void validateDates(Date from, Date to) {
		if (CommonUtil.isNull(from) || CommonUtil.isNull(to)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "From/To dates cannot be null");
		}
		from = DateUtil.getISTTimeInUTC(DateUtil.getStartOfDay(DateUtil.getUTCTimeInIST(from)));
		to = DateUtil.getISTTimeInUTC(DateUtil.getEndOfDay(DateUtil.getUTCTimeInIST(to)));
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(to);
		Calendar calFrom = Calendar.getInstance();
		calFrom.setTime(from);
		calFrom.add(Calendar.DAY_OF_YEAR, 90);
		if (calFrom.before(calTo)) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, "Search duration cannot be greater than 90 days");
		}
	}

}
