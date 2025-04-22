
		e4Context.set(IWorkbenchPart.class, part);
		e4Context.set(IStatusLineManager.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return getActionBars().getStatusLineManager();
			}
		});
