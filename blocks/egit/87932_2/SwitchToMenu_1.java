					wiz.setNewBranchName(newBranchName);
					WizardDialog dlg = new WizardDialog(
							e.display.getActiveShell(), wiz);
					dlg.setHelpAvailable(false);
					dlg.open();
					newBranchName = wiz.getNewBranchName();
