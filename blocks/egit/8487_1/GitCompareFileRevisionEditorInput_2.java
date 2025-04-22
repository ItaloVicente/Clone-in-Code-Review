		if (ancestor != null) {
			try {
				ancestor.cacheContents(monitor);
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
