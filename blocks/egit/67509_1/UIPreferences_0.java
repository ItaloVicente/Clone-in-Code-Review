		if (value == null) {
			return null;
		}
		final String[] values = value.split(","); //$NON-NLS-1$
		if (values.length != cnt) {
			return null;
		}
