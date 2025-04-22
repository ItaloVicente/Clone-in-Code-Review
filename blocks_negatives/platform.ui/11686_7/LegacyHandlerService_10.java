	public void initPreExecuteHook() {
		EHandlerService hs = eclipseContext.get(EHandlerService.class);
		if (hs instanceof HandlerServiceImpl) {
			HandlerServiceImpl.preExecute = new Object() {
				@Execute
				public void execute(IEclipseContext context, ParameterizedCommand command,
						@Optional @Named(HandlerServiceImpl.PARM_MAP) Map parms,
						@Optional Event trigger, @Optional IEvaluationContext staticContext) {
					if (command == null) {
						return;
					}
					IEvaluationContext appContext = staticContext;
					if (appContext == null) {
						appContext = new ExpressionContext(context);
					}
					ExecutionEvent event = new ExecutionEvent(command.getCommand(), parms, trigger,
							appContext);
					CommandProxy.firePreExecute(command.getCommand(), event);
				}
			};
		}

	}

