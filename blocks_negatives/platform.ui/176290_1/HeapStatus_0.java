	/**
	 * Returns the maximum memory limit, or Long.MAX_VALUE if the max is not known.
	 */
	private long getMaxMem() {
		long max = Long.MAX_VALUE;
		try {
			Object o = maxMemMethod.invoke(Runtime.getRuntime());
			if (o instanceof Long) {
				max = ((Long) o).longValue();
			}
		} catch (Exception e) {
		}
		return max;
	}

