		if (this == DIRTY) {
			return "DIRTY";
		}
		if (this == MISSING_FILE) {
			return "MISSING_FILE";
		}
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"
