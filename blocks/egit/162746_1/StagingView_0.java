		this.commitAndPushButton = toolkit.createButton(commitButtonsContainer,
				UIText.StagingView_CommitAndPush, SWT.PUSH);
		commitAndPushButton.setImage(getImage(UIIcons.PUSH));
		commitAndPushButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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
					Activator.logWarning(e.getMessage(), e);
				}
			}
		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(commitAndPushButton);

