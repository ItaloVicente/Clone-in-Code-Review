		this.mergeRadio = new Button(res, SWT.RADIO);
		this.mergeRadio.setText(UIText.UpstreamConfigComponent_MergeRadio);
		this.mergeRadio.setLayoutData(
				new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		this.mergeRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfig = UpstreamConfig.MERGE;
			}
		});
		this.rebaseRadio = new Button(res, SWT.RADIO);
		this.rebaseRadio.setText(UIText.UpstreamConfigComponent_RebaseRadio);
		this.rebaseRadio.setLayoutData(
				new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 3, 1));
		this.mergeRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfig = UpstreamConfig.REBASE;
			}
		});
		this.mergeRadio
				.setSelection(this.upstreamConfig == UpstreamConfig.MERGE);
		this.rebaseRadio
				.setSelection(this.upstreamConfig == UpstreamConfig.REBASE);
