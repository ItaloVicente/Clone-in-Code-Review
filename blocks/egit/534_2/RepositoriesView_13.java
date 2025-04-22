
					WizardDialog dlg = new WizardDialog(getSite().getShell(),
							new ConfigureRemoteWizard(node.getRepository(),
									configName, false));
			      if (dlg.open() == Window.OK)
					scheduleRefresh();


