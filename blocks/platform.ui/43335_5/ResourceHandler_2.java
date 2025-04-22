		} else {
			logger.error(new Exception(), // log a stack trace for debugging
					"Attempted to save a workbench model that had no top-level windows! " //$NON-NLS-1$
							+ "Skipped saving the model to avoid corruption."); //$NON-NLS-1$
		}
	}

	private boolean isSaveAllowed() {
		return saveAndRestore && hasTopLevelWindows();
