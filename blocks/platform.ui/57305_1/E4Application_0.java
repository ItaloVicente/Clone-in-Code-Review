		appContext.set(ESelectionService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return ContextInjectionFactory.make(SelectionServiceImpl.class, context);
			}
		});
