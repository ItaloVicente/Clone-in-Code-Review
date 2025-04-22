		if (invalidateAllDecorations || updateRoot) {
			try {
				root.setSessionProperty(REFRESH_KEY,
						Long.valueOf(System.currentTimeMillis()));
			} catch (CoreException e) {
				handleException(root, e);
			}
