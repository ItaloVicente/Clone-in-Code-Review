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
				try {
					PushBranchWizard wizard = new PushBranchWizard(repository,
							repository.resolve(Constants.HEAD));
					new WizardDialog(commitAndPushButton.getShell(), wizard)
							.open();
				} catch (RevisionSyntaxException | IOException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
