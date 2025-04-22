		warningComposite.setLayout(new GridLayout(2, false));
		warningComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
				true, false));
		warningLabel = new CLabel(warningComposite, SWT.NONE);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		warningLabel
				.setToolTipText(UIText.GitHistoryPage_IncompleteListTooltip);
