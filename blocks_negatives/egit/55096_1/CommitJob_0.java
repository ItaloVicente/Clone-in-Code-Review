						WizardDialog wizardDialog = new WizardDialog(display
								.getActiveShell(), pushWizard);
						wizardDialog.setHelpAvailable(true);
						wizardDialog.open();
					} catch (IOException e) {
						Activator.handleError(
								NLS.bind(UIText.CommitUI_pushFailedMessage, e),
								e, true);
					}
