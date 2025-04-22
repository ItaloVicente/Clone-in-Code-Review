				ParameterizedCommand cmd = ((MHandledMenuItem) element)
						.getWbCommand();
				EHandlerService handlerService = evalContext
						.get(EHandlerService.class);
				if (cmd != null && handlerService != null) {
					MHandledMenuItem item = (MHandledMenuItem) element;
					final IEclipseContext staticContext = EclipseContextFactory
							.create(MMRF_STATIC_CONTEXT);
					ContributionsAnalyzer.populateModelInterfaces(item,
							staticContext, item.getClass().getInterfaces());
					try {
						((MHandledMenuItem) element).setEnabled(handlerService
								.canExecute(cmd, staticContext));
					} finally {
						staticContext.dispose();
					}
