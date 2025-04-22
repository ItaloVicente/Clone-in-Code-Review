			long duration = System.currentTimeMillis() - startTime;
			if (duration > MAX_EARLY_STARTUP_RUN_DURATION_MILLIS) {
				String message = NLS.bind(
						WorkbenchMessages.EarlyStartupRunnable_ExtensionTookTooLong,
						new Object[] { executableExtension.getClass().getName() + ".earlyStartup", //$NON-NLS-1$
								duration, MAX_EARLY_STARTUP_RUN_DURATION_MILLIS });
				WorkbenchPlugin.log(new Status(IStatus.WARNING, message, null));
			}
