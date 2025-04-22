
	@Override
	public Repository getRepository() {
		return ResourceUtil
				.getRepository(Path.fromOSString(file.getAbsolutePath()));
	}
