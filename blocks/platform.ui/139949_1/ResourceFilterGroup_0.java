	private int getDateYear(Calendar calendar) {
		Date calendarDate = calendar.getTime();
		int calendarYear = calendar.get(Calendar.YEAR);
		if (calendarYear >= DateTime_MIN_YEAR) {
			return calendarYear;
		}
		gregorianCalendar.setTime(calendarDate);
		return gregorianCalendar.get(Calendar.YEAR);
	}

	private int getCalendarYear() {
		Calendar calendar = Calendar.getInstance();
		int calendarYear = calendar.get(Calendar.YEAR);
		int dateYear = argumentsDate.getYear();
		if (calendarYear >= DateTime_MIN_YEAR) {
			return dateYear;
		}

		gregorianCalendar.setTime(new Date());
		int currentYear = gregorianCalendar.get(Calendar.YEAR);
		return dateYear = calendarYear + (dateYear - currentYear);
	}

