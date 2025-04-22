			else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(
						new Exception(), // log a stack trace for debugging
						"applicationXMI parameter not set and no branding plugin defined. "); //$NON-NLS-1$
			}
