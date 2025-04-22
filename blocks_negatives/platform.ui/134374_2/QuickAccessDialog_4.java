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
								window.getShell().getDisplay().asyncExec(() -> element.execute());
							}
						}
					};
					restoreDialog();
					QuickAccessDialog.this.invokingCommand = invokingCommand;
					if (QuickAccessDialog.this.invokingCommand != null
							&& !QuickAccessDialog.this.invokingCommand.isDefined()) {
						QuickAccessDialog.this.invokingCommand = null;
					} else {
						getInvokingCommandKeySequences();
