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

		CLabel warningLabel = new CLabel(warningComposite, SWT.NONE);
		warningLabel.setText(UIText.CreateBranchPage_LocalBranchWarningText);
		warningLabel.setToolTipText(UIText.CreateBranchPage_LocalBranchWarningTooltip);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_INFO_TSK));

		buttonConfigRebase = new Button(upstreamConfigGroup, SWT.RADIO);
		buttonConfigRebase.setText(UIText.CreateBranchPage_RebaseRadioButton);
		buttonConfigRebase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (buttonConfigRebase.getSelection())
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
				if (buttonConfigMerge.getSelection())
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
				if (buttonConfigNone.getSelection())
					upstreamConfig = UpstreamConfig.NONE;
			}
		});
		buttonConfigNone
				.setToolTipText(UIText.CreateBranchPage_PullNoneTooltip);
