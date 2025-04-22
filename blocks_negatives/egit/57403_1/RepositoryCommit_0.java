	private static DateFormat FORMAT = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.SHORT);

	/**
	 * Format commit date
	 *
	 * @param date
	 * @return date string
	 */
	public static String formatDate(final Date date) {
		if (date == null)
		synchronized (FORMAT) {
			return FORMAT.format(date);
		}
	}

