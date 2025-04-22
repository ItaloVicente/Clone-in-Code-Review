	public final Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService srv = (ICommandService) part.getSite().getService(
				ICommandService.class);
		Command command = srv.getCommand(commandId);
		try {
			return command.executeWithChecks(event);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotDefinedException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotEnabledException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotHandledException e) {
			Activator.handleError(e.getMessage(), e, true);
