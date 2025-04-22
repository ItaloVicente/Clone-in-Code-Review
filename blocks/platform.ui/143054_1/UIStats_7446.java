		}
	}

	public static boolean isDebugging(int event) {
		return debug[event];
	}

	public static void start(int event, String label) {
		if (debug[event]) {
