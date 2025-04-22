								if (doActivateAdditionalRefs) {
									getContainer().getShell().getDisplay()
											.asyncExec(new Runnable() {

												public void run() {
													Activator
															.getDefault()
															.getPreferenceStore()
															.setValue(
																	UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS,
																	true);
												}
											});
								}
