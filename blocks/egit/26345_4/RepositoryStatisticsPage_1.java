	private String getStatsAsString(Properties stats, String key) {
		return bigIntFmt.format(firstNonNull(stats.get(key), "")); //$NON-NLS-1$
	}

	@SuppressWarnings("boxing")
	private static long getStatsAsLong(Properties stats, String key) {
		return (Long) firstNonNull(stats.get(key), 0);
	}

	private static <T> T firstNonNull(T first, T second) {
		return first != null ? first : second;
	}

