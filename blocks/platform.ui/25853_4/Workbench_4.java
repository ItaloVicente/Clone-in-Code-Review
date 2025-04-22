	private boolean detectWorkbenchCorruption(MApplication application) {
		if (application.getChildren().isEmpty()) {
			WorkbenchPlugin.log(
					"When auto-saving the workbench model, there were no top-level windows. " //$NON-NLS-1$
							+ " Skipped saving the model.", //$NON-NLS-1$
					new Exception()); // log a stack trace to assist debugging
			return true;
		}
		return false;
	}

