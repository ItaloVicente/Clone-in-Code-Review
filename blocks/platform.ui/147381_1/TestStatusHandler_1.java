	public static void install() throws Exception {
		Field fieldStatusHandler = StatusManager.class.getDeclaredField("statusHandler");
		fieldStatusHandler.setAccessible(true);
		fieldStatusHandler.set(StatusManager.getManager(), new TestStatusHandler());
	}

	public static void uninstall() throws Exception {
		Field fieldStatusHandler = StatusManager.class.getDeclaredField("statusHandler");
		fieldStatusHandler.setAccessible(true);
		fieldStatusHandler.set(StatusManager.getManager(), null);
	}

