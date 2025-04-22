
					new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									name, true)).open();
					scheduleRefresh();

