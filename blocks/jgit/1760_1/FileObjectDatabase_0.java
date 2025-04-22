	File fileFor(final AnyObjectId objectId) {
		return fileFor(objectId.name());
	}

	File fileFor(final String objectName) {
		final String d = objectName.substring(0
		final String f = objectName.substring(2);
		return new File(new File(getDirectory()
	}

