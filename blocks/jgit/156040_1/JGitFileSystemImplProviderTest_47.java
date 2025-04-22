		try {
			provider.checkAccess(path_not_exists);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}
