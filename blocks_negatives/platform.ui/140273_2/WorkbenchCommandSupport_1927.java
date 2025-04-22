	public final void addHandlerSubmission(
			final HandlerSubmission handlerSubmission) {
		final IHandlerActivation activation = handlerService.activateHandler(
				handlerSubmission.getCommandId(), new LegacyHandlerWrapper(
						handlerSubmission.getHandler()),
				new LegacyHandlerSubmissionExpression(handlerSubmission
						.getActivePartId(), handlerSubmission.getActiveShell(),
						handlerSubmission.getActiveWorkbenchPartSite()));
