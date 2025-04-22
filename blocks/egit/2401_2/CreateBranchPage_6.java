		upstreamConfigGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		upstreamConfigGroup
				.setToolTipText(UIText.CreateBranchPage_PullStrategyTooltip);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).applyTo(
				upstreamConfigGroup);
		upstreamConfigGroup
				.setText(UIText.CreateBranchPage_PullStrategyGroupHeader);
		upstreamConfigGroup.setLayout(new GridLayout(1, false));

		warningComposite = new Composite(upstreamConfigGroup, SWT.NONE);
		warningComposite.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(
				warningComposite);
		Label warningLabel = new Label(warningComposite, SWT.NONE);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_INFO_TSK));
		Text warningText = new Text(warningComposite, SWT.READ_ONLY);
		warningText.setText(UIText.CreateBranchPage_LocalBranchWarningText);
		warningText
				.setToolTipText(UIText.CreateBranchPage_LocalBranchWarningTooltip);

		buttonConfigRebase = new Button(upstreamConfigGroup, SWT.RADIO);
		buttonConfigRebase.setText(UIText.CreateBranchPage_RebaseRadioButton);
		buttonConfigRebase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfig = UpstreamConfig.REBASE;
			}
		});
		buttonConfigRebase
				.setToolTipText(UIText.CreateBranchPage_PullRebaseTooltip);

		buttonConfigMerge = new Button(upstreamConfigGroup, SWT.RADIO);
		buttonConfigMerge.setText(UIText.CreateBranchPage_MergeRadioButton);
		buttonConfigMerge.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfig = UpstreamConfig.MERGE;
			}
		});
		buttonConfigMerge
				.setToolTipText(UIText.CreateBranchPage_PullMergeTooltip);

		buttonConfigNone = new Button(upstreamConfigGroup, SWT.RADIO);
		buttonConfigNone.setText(UIText.CreateBranchPage_NoneRadioButton);
		buttonConfigNone.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfig = UpstreamConfig.NONE;
			}
		});
		buttonConfigNone
				.setToolTipText(UIText.CreateBranchPage_PullNoneTooltip);

		switch (defaultUpstreamConfig) {
		case REBASE:
			buttonConfigRebase.setSelection(true);
			break;
		case MERGE:
			buttonConfigMerge.setSelection(true);
			break;
		case NONE:
			buttonConfigNone.setSelection(true);
			break;
		}

