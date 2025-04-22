							Runnable methodRunnable = () -> {
								try {
									methodToExecute.invoke(focusComponent);
								} catch (final IllegalAccessException e1) {
								} catch (final InvocationTargetException e2) {
									focusControl.getDisplay().asyncExec(
											() -> ExceptionHandler
													.getInstance()
													.handleException(
															new ExecutionException(
																	"An exception occurred while executing " //$NON-NLS-1$
																			+ methodToExecute
																					.getName(),
																	e2
																			.getTargetException())));
