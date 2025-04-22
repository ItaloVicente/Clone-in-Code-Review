				if (canPushHeadOnly()) {
					pushHead(currentRepository);
				} else {
					commit(true);
				}
			}

			private void pushHead(Repository repository) {
				if (repository == null) {
					return;
				}
				PushMode mode = getPushMode();
				if (mode == null) {
					return;
				}
				try {
					Wizard wizard = mode.getWizard(repository, null);
					if (wizard != null) {
						PushWizardDialog dialog = new PushWizardDialog(
								commitAndPushButton.getShell(), wizard);
						dialog.open();
					}
				} catch (IOException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
