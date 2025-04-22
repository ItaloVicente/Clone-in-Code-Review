			if (currentSnapshot instanceof ExpressionContext) {
				IEclipseContext ctx = ((ExpressionContext) currentSnapshot).eclipseContext;
				commandImageService = ctx.get(ICommandImageService.class);
			} else {
				commandImageService = PlatformUI.getWorkbench().getService(ICommandImageService.class);
			}
