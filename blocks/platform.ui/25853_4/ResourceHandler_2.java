			if (!hasTopLevelWindows(resource)) {
				if (logger != null) {
					logger.error(new Exception(), // log a stack trace to help debug the corruption
							"The persisted workbench has no top-level windows, so reinitializing with defaults."); //$NON-NLS-1$
				}
				resource = null;
			}
