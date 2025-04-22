		int timeStart = 0;
		if (time.startsWith("@"))
			timeStart = 1;
		else
			timeStart = 0;
		long when = Long
				.parseLong(time.substring(timeStart
