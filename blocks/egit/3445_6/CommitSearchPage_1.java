		ToolBar checkBar = new ToolBar(repositoryGroup, SWT.FLAT | SWT.VERTICAL);
		GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.TOP)
				.grab(false, true).applyTo(checkBar);
		ToolItem checkItem = new ToolItem(checkBar, SWT.PUSH);
		checkItem.setToolTipText(UIText.CommitSearchPage_CheckAll);
		Image checkImage = UIIcons.CHECK_ALL.createImage();
		UIUtils.hookDisposal(checkItem, checkImage);
		checkItem.setImage(checkImage);
		checkItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				repositoryViewer.setAllChecked(true);
				repositoryGroup.setText(getRepositoryText());
			}

		});
		ToolItem uncheckItem = new ToolItem(checkBar, SWT.PUSH);
		uncheckItem.setToolTipText(UIText.CommitSearchPage_UncheckAll);
		Image uncheckImage = UIIcons.UNCHECK_ALL.createImage();
		UIUtils.hookDisposal(uncheckItem, uncheckImage);
		uncheckItem.setImage(uncheckImage);
		uncheckItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				repositoryViewer.setAllChecked(false);
				repositoryGroup.setText(getRepositoryText());
			}

		});

		this.searchAllBranchesButton = new Button(repositoryGroup, SWT.CHECK);
