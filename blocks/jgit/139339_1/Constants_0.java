	public static <T> T checkNotNull(T arg) {
		if (arg == null) {
			throw new NullPointerException();
		}
		return arg;
	}

