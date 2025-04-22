
	public static void executeCommand(IHandlerService service, String id) {
		executeCommand(service, id, null);
	}

	public static void executeCommand(IHandlerService service, String id,
			Event event) {
		try {
			service.executeCommand(id, event);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
		} catch (NotDefinedException e) {
			Activator.handleError(e.getMessage(), e, false);
		} catch (NotEnabledException e) {
			Activator.handleError(e.getMessage(), e, false);
		} catch (NotHandledException e) {
			Activator.handleError(e.getMessage(), e, false);
		}
	}
