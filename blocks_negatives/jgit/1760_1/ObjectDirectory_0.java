		return fileFor(objectId.name());
	}

	private File fileFor(final String objectName) {
		final String d = objectName.substring(0, 2);
		final String f = objectName.substring(2);
		return new File(new File(objects, d), f);
