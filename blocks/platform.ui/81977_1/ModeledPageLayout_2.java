		logDeprecatedWarning(viewId);
	}

	private void logDeprecatedWarning(String viewId) {
		String message = viewId + ": Deprecated relationship \"fast\" should be converted to \"stack\"."; //$NON-NLS-1$
		WorkbenchPlugin.log(message, StatusUtil.newStatus(IStatus.WARNING, message, null));
