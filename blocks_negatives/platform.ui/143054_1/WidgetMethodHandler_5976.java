									focusControl.getDisplay().asyncExec(
											() -> ExceptionHandler
													.getInstance()
													.handleException(
															new ExecutionException(
																			+ methodToExecute
																					.getName(),
																	e2
																			.getTargetException())));
