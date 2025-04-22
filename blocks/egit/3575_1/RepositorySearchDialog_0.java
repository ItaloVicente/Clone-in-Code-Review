		checkAllItem = new ToolItem(toolbar, SWT.PUSH);
		checkAllItem
				.setToolTipText(UIText.RepositorySearchDialog_CheckAllRepositories);
		checkAllItem.setEnabled(false);
		Image checkImage = UIIcons.CHECK_ALL.createImage();
		UIUtils.hookDisposal(checkAllItem, checkImage);
		checkAllItem.setImage(checkImage);
		checkAllItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				fTreeViewer.setAllChecked(true);
				enableOk();
			}

		});

		uncheckAllItem = new ToolItem(toolbar, SWT.PUSH);
		uncheckAllItem
				.setToolTipText(UIText.RepositorySearchDialog_UncheckAllRepositories);
		uncheckAllItem.setEnabled(false);
		Image uncheckImage = UIIcons.UNCHECK_ALL.createImage();
		UIUtils.hookDisposal(uncheckAllItem, uncheckImage);
		uncheckAllItem.setImage(uncheckImage);
		uncheckAllItem.addSelectionListener(new SelectionAdapter() {
