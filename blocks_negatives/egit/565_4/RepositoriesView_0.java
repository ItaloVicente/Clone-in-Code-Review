
					new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									name, false)).open();
					scheduleRefresh();

