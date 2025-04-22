	private boolean isImpExpEnabled() {
		if (impExpEnabled == null) {
			impExpEnabled = Boolean.parseBoolean(System.getProperty("e4.impExpPerspectiveEnabled")); //$NON-NLS-1$
		}
		return impExpEnabled;
	}

