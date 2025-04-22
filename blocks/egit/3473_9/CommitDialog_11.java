		filesViewer.addCheckStateListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				updateFileSectionText();
			}
		});

		showUntrackedItem = new ToolItem(filesToolbar, SWT.CHECK);
		Image showUntrackedImage = UIIcons.UNTRACKED_FILE.createImage();
		UIUtils.hookDisposal(showUntrackedItem, showUntrackedImage);
		showUntrackedItem.setImage(showUntrackedImage);
		showUntrackedItem
				.setToolTipText(UIText.CommitDialog_ShowUntrackedFiles);
		IDialogSettings settings = org.eclipse.egit.ui.Activator.getDefault()
				.getDialogSettings();
		if (settings.get(SHOW_UNTRACKED_PREF) != null) {
			showUntracked = Boolean.valueOf(settings.get(SHOW_UNTRACKED_PREF))
					.booleanValue();
		}
		showUntrackedItem.setSelection(showUntracked);
		showUntrackedItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				showUntracked = showUntrackedItem.getSelection();
				filesViewer.refresh(true);
				updateFileSectionText();
			}

		});

		ToolItem checkAllItem = new ToolItem(filesToolbar, SWT.PUSH);
		Image checkImage = UIIcons.CHECK_ALL.createImage();
		UIUtils.hookDisposal(checkAllItem, checkImage);
		checkAllItem.setImage(checkImage);
		checkAllItem.setToolTipText(UIText.CommitDialog_SelectAll);
		checkAllItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				filesViewer.setAllChecked(true);
				updateFileSectionText();
			}

		});

		ToolItem uncheckAllItem = new ToolItem(filesToolbar, SWT.PUSH);
		Image uncheckImage = UIIcons.UNCHECK_ALL.createImage();
		UIUtils.hookDisposal(uncheckAllItem, uncheckImage);
		uncheckAllItem.setImage(uncheckImage);
		uncheckAllItem.setToolTipText(UIText.CommitDialog_DeselectAll);
		uncheckAllItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				filesViewer.setAllChecked(false);
				updateFileSectionText();
			}

		});

