
	private void resetOsNames() {
		Field field;
		try {
			field = SystemReader.class.getDeclaredField("isWindows");
			field.setAccessible(true);
			field.set(null
			field = SystemReader.class.getDeclaredField("isMacOS");
			field.setAccessible(true);
			field.set(null
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
