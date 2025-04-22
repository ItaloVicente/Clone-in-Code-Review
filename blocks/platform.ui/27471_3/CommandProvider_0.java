	EHandlerService getEHandlerService() {
		if (ehandlerService == null) {
			if (currentSnapshot instanceof ExpressionContext) {
				IEclipseContext ctx = ((ExpressionContext) currentSnapshot).eclipseContext;
				ehandlerService = ctx.get(EHandlerService.class);
			} else {
				ehandlerService = (EHandlerService) PlatformUI.getWorkbench().getService(
						EHandlerService.class);
			}
		}
		return ehandlerService;
	}

	ICommandService getCommandService() {
		if (commandService == null) {
			if (currentSnapshot instanceof ExpressionContext) {
				IEclipseContext ctx = ((ExpressionContext) currentSnapshot).eclipseContext;
				commandService = ctx.get(ICommandService.class);
			} else {
				commandService = (ICommandService) PlatformUI.getWorkbench().getService(
						ICommandService.class);
			}
		}
		return commandService;
	}

