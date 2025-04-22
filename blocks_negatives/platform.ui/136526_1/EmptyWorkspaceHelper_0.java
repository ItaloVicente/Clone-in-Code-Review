			final FormToolkit toolkit = new FormToolkit(emptyArea.getDisplay());
			emptyArea.addDisposeListener(e -> toolkit.dispose());
			final Color linkColor = JFaceColors.getHyperlinkText(emptyArea.getDisplay());

			for (IAction action : projectWizardActions) {
				createOption(optionsArea, toolkit, linkColor, action, action.getImageDescriptor().createImage(),
						action.getDescription());
			}

			createOption(optionsArea, toolkit, linkColor, newProjectAction,
					newProjectAction.getImageDescriptor().createImage(),
					ResourceNavigatorMessages.EmptyWorkspaceHelper_createGeneralProject);
		} else {
			messageLabel.setText(ResourceNavigatorMessages.EmptyWorkspaceHelper_noProjectsAvailableCreateOne);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(messageLabel);

			messageLabel.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					newProjectAction.run();
				}
			});
