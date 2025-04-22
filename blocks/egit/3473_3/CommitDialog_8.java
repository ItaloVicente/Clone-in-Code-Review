
		ToolBar filesToolbar = new ToolBar(container, SWT.FLAT | SWT.VERTICAL);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.TOP)
				.applyTo(filesToolbar);

		showUntrackedItem = new ToolItem(filesToolbar, SWT.CHECK);
		Image showUntrackedImage = new DecorationOverlayIcon(PlatformUI
				.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJ_FILE), UIIcons.OVR_UNTRACKED,
				IDecoration.BOTTOM_RIGHT).createImage();
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
			}

		});

