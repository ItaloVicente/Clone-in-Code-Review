				result.setProperty(IProgressConstants.ACTION_PROPERTY, new Action("Pop up a dialog") { //$NON-NLS-1$
					public void run() {
						MessageDialog.openInformation(getSite().getShell(), "Goto Action", //$NON-NLS-1$
								"The job can have an action associated with it"); //$NON-NLS-1$
					}
				});
