	public static void install() throws Exception {
		if (isInstalled) {
			return;
		}
		Field fieldStatusHandler = StatusManager.class.getDeclaredField("statusHandler");
		fieldStatusHandler.setAccessible(true);
		StatusManager statusManager = StatusManager.getManager();
		originalInstance = fieldStatusHandler.get(statusManager);
		fieldStatusHandler.set(statusManager, new TestStatusHandler());
		fieldStatusHandler.setAccessible(false);
		isInstalled = true;
	}

	public static void uninstall() throws Exception {
		if (!isInstalled) {
			return;
		}
		Field fieldStatusHandler = StatusManager.class.getDeclaredField("statusHandler");
		fieldStatusHandler.setAccessible(true);
		StatusManager statusManager = StatusManager.getManager();
		fieldStatusHandler.set(statusManager, originalInstance);
		fieldStatusHandler.setAccessible(false);
		isInstalled = false;
		originalInstance = null;
	}

