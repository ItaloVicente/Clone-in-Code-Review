		DateTime dateTime = (DateTime) source;

		Calendar cal = (Calendar) calendar.get();
		cal.setTime((Date) value);
		if ((dateTime.getStyle() & SWT.TIME) != 0) {
			dateTime.setTime(cal.get(Calendar.HOUR_OF_DAY), cal
