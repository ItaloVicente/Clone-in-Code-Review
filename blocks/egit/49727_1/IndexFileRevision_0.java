		try {
			return locateBlobObjectStamp();
		} catch (CoreException e) {
			e.printStackTrace();
			return -1;
		}
