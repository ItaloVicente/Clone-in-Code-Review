	}

	public static void end(int event, Object blame, String label) {
		if (debug[event]) {
			Long startTime = (Long) operations.remove(event + label);
			if (startTime == null) {
