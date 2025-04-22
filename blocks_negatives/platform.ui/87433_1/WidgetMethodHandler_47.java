							Runnable methodRunnable = new Runnable() {
								@Override
								public void run() {
									try {
										methodToExecute.invoke(focusComponent);
									} catch (final IllegalAccessException e) {
									} catch (final InvocationTargetException e) {
										/*
										 * I would like to log this exception --
										 * and possibly show a dialog to the
										 * user -- but I have to go back to the
										 * SWT event loop to do this. So, back
										 * we go....
										 */
										focusControl.getDisplay().asyncExec(
												new Runnable() {
													@Override
													public void run() {
														ExceptionHandler
																.getInstance()
																.handleException(
																		new ExecutionException(
																						+ methodToExecute
																								.getName(),
																				e
																						.getTargetException()));
													}
												});
									}
