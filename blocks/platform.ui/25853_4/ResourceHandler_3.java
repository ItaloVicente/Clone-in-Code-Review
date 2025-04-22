			if (!hasTopLevelWindows(applicationResource) && logger != null) {
				logger.error(
						new Exception(), // log a stack trace to help debug the corruption
						"Initializing from the application definition instance yields no top-level windows! " //$NON-NLS-1$
								+ "Continuing execution, but the missing windows may cause other initialization failures."); //$NON-NLS-1$
			}
