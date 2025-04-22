								EHandlerService service = getFromContext(context,
										EHandlerService.class);
								ICommandService commandService = getFromContext(context,
										ICommandService.class);
								if (service == null || commandService == null) {
									WorkbenchPlugin
											.log("Could not retrieve EHandlerService or ICommandService from context evaluation context."); //$NON-NLS-1$
									return EvaluationResult.FALSE;
								}
