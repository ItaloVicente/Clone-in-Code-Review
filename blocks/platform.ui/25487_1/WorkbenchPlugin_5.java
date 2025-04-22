		context.set(IHelpService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (helpService == null) {
					helpService = new HelpServiceImpl();
				}
				return helpService;
			}
		});
		context.set(ICommandHelpService.class.getName(), new ContextFunction() {
			@Override
			public Object compute(IEclipseContext context, String contextKey) {
				if (commandHelpService == null) {
					commandHelpService = ContextInjectionFactory.make(CommandHelpServiceImpl.class,
							e4Context);
				}
				return commandHelpService;
			}
		});
