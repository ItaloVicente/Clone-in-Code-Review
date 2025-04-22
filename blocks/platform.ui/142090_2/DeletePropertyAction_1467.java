							} else // shouldn't happen, but handle error condition
								MessageDialog.openError(Display.getDefault().getActiveShell(),
														"Error Deleting Property",  //$NON-NLS-1$
														"The properties file was not accessible!");   //$NON-NLS-1$

						} else // shouldn't happen, but handle error condition
							MessageDialog.openError(Display.getDefault().getActiveShell(),
										"Error Deleting Property",  //$NON-NLS-1$
										"The element that was selected was not of the right type.");   //$NON-NLS-1$
					} else // shouldn't happen, but handle error condition
						MessageDialog.openError(Display.getDefault().getActiveShell(),
									"Error Deleting Property",  //$NON-NLS-1$
									"An invalid number of properties were selected.");   //$NON-NLS-1$
