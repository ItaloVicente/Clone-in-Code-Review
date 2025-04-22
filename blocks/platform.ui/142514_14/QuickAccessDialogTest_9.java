		Arrays.stream(Display.getDefault().getShells()).filter(isQuickAccessShell).forEach(Shell::close);
	}

	static Optional<QuickAccessDialog> findQuickAccessDialog() {
		return Arrays.stream(Display.getDefault().getShells()).filter(isQuickAccessShell).findAny().map(Shell::getData)
				.map(QuickAccessDialog.class::cast);
