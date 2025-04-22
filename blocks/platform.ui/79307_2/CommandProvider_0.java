	public ICommandImageService getCommandImageService() {
		if (commandImageService == null) {
			if (currentSnapshot instanceof ExpressionContext) {
				IEclipseContext ctx = ((ExpressionContext) currentSnapshot).eclipseContext;
				commandImageService = ctx.get(ICommandImageService.class);
			} else {
				commandImageService = PlatformUI.getWorkbench().getService(ICommandImageService.class);
			}
		}
		return commandImageService;
	}

