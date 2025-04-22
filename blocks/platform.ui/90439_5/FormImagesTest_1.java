
	private static FormImages getFormImagesInstance() throws Exception {
		if (instance == null) {
			Constructor<FormImages> constructor = FormImages.class.getDeclaredConstructor();
			constructor.setAccessible(true);
			instance = constructor.newInstance();
		}
		return instance;
	}
