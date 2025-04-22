	private static String getOrdinal(int squashCount) {
		String ordinal = "th";
		switch (squashCount % 10) {
		case 1:
			ordinal = "st";
			break;
		case 2:
			ordinal = "nd";
			break;
		case 3:
			ordinal = "rd";
			break;
		}
		return ordinal;
	}

