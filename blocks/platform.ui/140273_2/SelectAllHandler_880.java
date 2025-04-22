									focusControl.getDisplay()
											.asyncExec(() -> ExceptionHandler.getInstance()
													.handleException(new ExecutionException(
															"An exception occurred while executing " //$NON-NLS-1$
																	+ methodToExecute.getName(),
															e2.getTargetException())));
