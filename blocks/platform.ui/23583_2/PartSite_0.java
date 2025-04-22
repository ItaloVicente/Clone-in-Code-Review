		serviceLocator.registerService(IWorkbenchPart.class, getPart());

		e4Context.set(IStatusLineManager.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return getActionBars().getStatusLineManager();
			}
		});
