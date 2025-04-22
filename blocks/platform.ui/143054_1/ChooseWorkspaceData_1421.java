					+ File.separator + "workspace"); //$NON-NLS-1$
		}
		return initialDefault;
	}

	private void setInitialDefault(String dir) {
		if (dir == null || dir.length() <= 0) {
			initialDefault = null;
			return;
