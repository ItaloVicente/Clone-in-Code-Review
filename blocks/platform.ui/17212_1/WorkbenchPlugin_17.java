		context.set(FontRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return Workbench.getInstance().getThemeManager().getCurrentTheme()
						.getFontRegistry();
			}
		});
		context.set(ColorRegistry.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				return Workbench.getInstance().getThemeManager().getCurrentTheme()
						.getColorRegistry();
			}
		});
