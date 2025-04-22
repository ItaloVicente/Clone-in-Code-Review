
		if ("canEnableLfs".equals(property)) { //$NON-NLS-1$
			if (LfsFactory.getInstance().isAvailable()) {
				return !LfsFactory.getInstance().isEnabled(repository);
			}
		}

