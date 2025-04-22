				if (wiz == null) {
					wiz = new CreateBranchWizard(repository);
				}
				WizardDialog dlg = new WizardDialog(e.display.getActiveShell(),
						wiz);
				dlg.setHelpAvailable(false);
				dlg.open();
