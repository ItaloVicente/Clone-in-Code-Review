	ICommandImageService getCommandImageService() {
		if (commandImageService == null) {
			if (currentSnapshot instanceof ExpressionContext) {
				IEclipseContext ctx = ((ExpressionContext) currentSnapshot).eclipseContext;
				commandImageService = ctx.get(CommandImageService.class);
			} else {
				commandImageService = PlatformUI.getWorkbench().getService(CommandImageService.class);
			}
		}
		return commandImageService;
	}

