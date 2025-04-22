		warningComposite.setLayout(new GridLayout(3, false));
		warningComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		Label warningLabel = new Label(warningComposite, SWT.NONE);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		warningText = new Text(warningComposite, SWT.READ_ONLY);
		warningText.setToolTipText(UIText.GitHistoryPage_IncompleteListTooltip);
