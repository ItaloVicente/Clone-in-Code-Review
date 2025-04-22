			cal.set(Calendar.HOUR_OF_DAY, dateTime.getHours());
			cal.set(Calendar.MINUTE, dateTime.getMinutes());
			cal.set(Calendar.SECOND, dateTime.getSeconds());
		} else {
			cal.set(Calendar.YEAR, dateTime.getYear());
			cal.set(Calendar.MONTH, dateTime.getMonth());
			cal.set(Calendar.DAY_OF_MONTH, dateTime.getDay());
