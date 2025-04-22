	private static void setEnabledRecursively(final Control control,
			final boolean enable) {
		control.setEnabled(enable);
		if (control instanceof Composite)
			for (final Control child : ((Composite) control).getChildren())
				setEnabledRecursively(child, enable);
	}

