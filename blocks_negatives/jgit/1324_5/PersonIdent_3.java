		}
		final int sp = in.indexOf(' ', gt + 2);
		if (sp == -1) {
			when = 0;
			tzOffset = -1;
		} else {
			final String tzHoursStr = in.substring(sp + 1, sp + 4).trim();
			final int tzHours;
			if (tzHoursStr.charAt(0) == '+') {
				tzHours = Integer.parseInt(tzHoursStr.substring(1));
			} else {
				tzHours = Integer.parseInt(tzHoursStr);
			}
			final int tzMins = Integer.parseInt(in.substring(sp + 4).trim());
			when = Long.parseLong(in.substring(gt + 1, sp).trim()) * 1000;
			tzOffset = tzHours * 60 + tzMins;
		}
