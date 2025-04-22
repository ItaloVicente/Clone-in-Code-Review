	/**
	 * @param manager
	 * @param b
	 */
	void setCommandFireEvents(CommandManager manager, boolean b) {
		try {
			f.setAccessible(true);
			f.set(manager, Boolean.valueOf(b));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException| IllegalAccessException  e) {
			e.printStackTrace();
		}
	}
