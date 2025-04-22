	/** Thrown if the update indices in the stack are not monotonic */
	public static class ReftableNumbersNotIncreasingException
			extends RuntimeException {
		private static final long serialVersionUID = 1L;

		String name;

		long lastMax;

		long min;

		ReftableNumbersNotIncreasingException(String name, long lastMax,
				long min) {
			this.name = name;
			this.lastMax = lastMax;
			this.min = min;
		}
	}

