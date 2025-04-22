		try {
			provider.createDirectory(path);
			failBecauseExceptionWasNotThrown(FileAlreadyExistsException.class);
		} catch (FileAlreadyExistsException ignored) {
		}
	}
