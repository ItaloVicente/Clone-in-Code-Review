				if (isPageComplete()) {
					IWizardContainer container = getContainer();
					IWizardPage next = getNextPage();
					if (next != null) {
						container.showPage(next);
					} else if (container instanceof FinishableWizardDialog) {
						((FinishableWizardDialog) container).finish();
					}
				}
