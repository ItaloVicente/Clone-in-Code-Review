		try {
			provider.newDirectoryStream(path
			failBecauseExceptionWasNotThrown(NotDirectoryException.class);
		} catch (NotDirectoryException ignored) {
		}
		try {
			provider.newDirectoryStream(crazyPath
			failBecauseExceptionWasNotThrown(NotDirectoryException.class);
		} catch (NotDirectoryException ignored) {
		}
