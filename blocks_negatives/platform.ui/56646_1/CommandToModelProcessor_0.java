	void setCommandFireEvents(CommandManager manager, boolean b) {
		try {
			f.setAccessible(true);
			f.set(manager, Boolean.valueOf(b));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
