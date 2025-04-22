							@Override
							protected void handleElementSelected(String text, Object selectedElement) {
								if (selectedElement instanceof QuickAccessElement) {
									addPreviousPick(text, selectedElement);
									storeDialog(getDialogSettings());

									/*
									 * Execute after the dialog has been fully
									 * closed/disposed and the correct
									 * EclipseContext is in place.
									 */
									final QuickAccessElement element = (QuickAccessElement) selectedElement;
									window.getShell().getDisplay().asyncExec(new Runnable() {
										@Override
										public void run() {
											element.execute();
										}
									});
								}
