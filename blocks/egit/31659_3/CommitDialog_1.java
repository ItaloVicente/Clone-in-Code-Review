		Link link = new Link(parent, SWT.WRAP | SWT.NO_FOCUS);
		((GridLayout) parent.getLayout()).numColumns++;
		link.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		link.setText(UIText.CommitDialog_OpenStagingViewLink);
		link.setToolTipText(UIText.CommitDialog_OpenStagingViewToolTip);
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openStagingViewLinkClicked();
			}
		});

		toolkit.adapt(link, false, false);
		return link;
	}

	private void openStagingViewLinkClicked() {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
		try {
			StagingView view = (StagingView) workbenchPage
					.showView(StagingView.VIEW_ID);
			view.reload(repository);
			String message = commitMessageComponent.getCommitMessage();
			if (message != null && message.length() > 0)
				view.setCommitMessage(message);
			setReturnCode(CANCEL);
			close();
		} catch (PartInitException e) {
			Activator.handleError(UIText.CommitDialog_OpenStagingViewError, e,
					true);
		}
