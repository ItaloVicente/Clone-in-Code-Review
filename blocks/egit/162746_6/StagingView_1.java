				if (canPushHeadOnly()) {
					pushHead(currentRepository);
				} else {
					commit(true);
				}
			}

			private void pushHead(final Repository repository) {
				if (repository == null) {
					return;
				}
				PushBranchActionHandler.openPushHEADWizard(repository,
						commitAndPushButton.getShell());
