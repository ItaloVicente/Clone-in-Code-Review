					parent.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							loading = false;
							getWizard().getContainer().updateButtons();
						}

