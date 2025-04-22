	private String getStatsAsString(Properties stats, String key) {
		return bigIntFmt.format(firstNonNull(stats.get(key), "")); //$NON-NLS-1$
	}

	private static long getStatsAsLong(Properties stats, String key) {
		Object value = stats.get(key);
		if (value instanceof Number)
			return ((Number) value).longValue();
		return 0L;
	}

	private static <T> T firstNonNull(T first, T second) {
		return first != null ? first : second;
	}

