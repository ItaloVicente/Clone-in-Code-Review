		gerritConfiguration = new GerritConfigurationPage() {

			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(cloneSource.getSelection());
				super.setVisible(visible);
			}
		};
		gerritConfiguration.setHelpContext(HELP_CONTEXT);
	}

	/**
	 * @param newValue
	 *            if true the clone wizard just creates a clone operation. The
	 *            caller has to run this operation using runCloneOperation. If
	 *            false the clone operation is performed using a job.
	 */
	public void setCallerRunsCloneOperation(boolean newValue) {
		callerRunsCloneOperation = newValue;
