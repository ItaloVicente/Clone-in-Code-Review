									focusControl.getDisplay().asyncExec(
											() -> {
												if (!focusControl
														.isDisposed()) {
													focusControl
															.notifyListeners(
																	SWT.Selection,
																	null);
												}
											});
