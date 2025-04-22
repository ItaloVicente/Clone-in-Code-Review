
					WizardDialog dlg = new WizardDialog(getSite().getShell(), new NewRemoteWizard(
							node.getRepository()));
                    if (dlg.open() == Window.OK)
					scheduleRefresh();


