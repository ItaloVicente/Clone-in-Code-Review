		final String tzHoursStr = in.substring(tzSeparator + 1
		final int tzHours;
		if (tzHoursStr.charAt(0) == '+')
			tzHours = Integer.parseInt(tzHoursStr.substring(1));
		else
			tzHours = Integer.parseInt(tzHoursStr);

		final int tzMins = Integer.parseInt(in.substring(tzSeparator + 4).trim());
		when = Long.parseLong(in.substring(whenSeparator + 1
		tzOffset = tzHours * 60 + tzMins;
