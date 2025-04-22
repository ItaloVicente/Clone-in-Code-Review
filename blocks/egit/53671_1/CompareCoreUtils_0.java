		if (resource instanceof IEncodedStorage) {
			IEncodedStorage encodedStorage = ((IEncodedStorage) resource);
			try {
				charset = encodedStorage.getCharset();
				if (charset == null) {
					charset = resource.getParent().getDefaultCharset();
				}
			} catch (CoreException e) {
				charset = ResourcesPlugin.getEncoding();
			}
		} else if (resource instanceof IContainer) {
			try {
				charset = ((IContainer) resource).getDefaultCharset();
			} catch (CoreException e) {
				charset = ResourcesPlugin.getEncoding();
			}
		} else {
			charset = ResourcesPlugin.getEncoding();
