			if (currentSnapshot instanceof ExpressionContext) {
				IEclipseContext ctx = ((ExpressionContext) currentSnapshot).eclipseContext;
				handlerService = ctx.get(IHandlerService.class);
			} else {
				handlerService = (IHandlerService) PlatformUI.getWorkbench().getService(
						IHandlerService.class);
			}
