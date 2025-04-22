			HandlerServiceImpl.handlerGenerator = new ContextFunction() {
				@Override
				public Object compute(IEclipseContext context, String contextKey) {
					return new WorkbenchHandlerServiceHandler(contextKey);
				}
			};
