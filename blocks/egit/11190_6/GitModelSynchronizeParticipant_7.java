		return input;
	}

	private static String getLocalEncoding(IResource resource) {
		if (resource instanceof IEncodedStorage) {
			IEncodedStorage es = (IEncodedStorage) resource;
			try {
				return es.getCharset();
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return null;
