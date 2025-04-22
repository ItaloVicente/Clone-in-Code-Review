	}

	public static boolean getEarlyStartupCalled() {
		return earlyStartupCalled;
	}

	public static boolean getEarlyStartupCompleted() {
		return earlyStartupCompleted;
	}

	public static void reset() {
		earlyStartupCalled = false;
		earlyStartupCompleted = false;
	}
