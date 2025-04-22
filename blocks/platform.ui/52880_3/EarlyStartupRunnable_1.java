		if (executableExtension instanceof IStartup) {
			String methodName = executableExtension.getClass().getName() + ".earlyStartup"; //$NON-NLS-1$
			try {
				UIStats.start(UIStats.EARLY_STARTUP, methodName);
				((IStartup) executableExtension).earlyStartup();
			} finally {
				UIStats.end(UIStats.EARLY_STARTUP, executableExtension, methodName);
			}
