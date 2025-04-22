	public void run(IAction action) {
		ICommandService srv = (ICommandService) part.getSite().getService(
				ICommandService.class);
		IHandlerService hsrv = (IHandlerService) part.getSite().getService(
				IHandlerService.class);
		Command command = srv.getCommand(commandId);

		IEvaluationContext context = hsrv.createContextSnapshot(true);

		try {
			hsrv.executeCommandInContext(
					new ParameterizedCommand(command, null), null, context);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotDefinedException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotEnabledException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotHandledException e) {
			Activator.handleError(e.getMessage(), e, true);
