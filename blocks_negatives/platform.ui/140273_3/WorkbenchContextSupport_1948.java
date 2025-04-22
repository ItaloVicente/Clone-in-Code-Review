	public final void addEnabledSubmission(
			final EnabledSubmission enabledSubmission) {
		final IContextActivation activation = contextService.activateContext(
				enabledSubmission.getContextId(),
				new LegacyHandlerSubmissionExpression(enabledSubmission
						.getActivePartId(), enabledSubmission.getActiveShell(),
						enabledSubmission.getActiveWorkbenchPartSite()));
