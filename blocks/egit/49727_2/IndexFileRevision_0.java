		try {
			return locateBlobObjectStamp();
		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
			return -1;
		}
