			if (!(handler instanceof ResourceHandler)
					|| ((ResourceHandler) handler).hasTopLevelWindows()) {
				handler.save();
			} else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(
						new Exception(), // log a stack trace for debugging
						"Attempted to save a workbench model that had no top-level windows! " //$NON-NLS-1$
								+ "Skipped saving the model to avoid corruption."); //$NON-NLS-1$
			}
