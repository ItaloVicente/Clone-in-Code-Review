		mergeRadio = new Button(upstreamConfigGroup, SWT.RADIO);
		mergeRadio.setText(UIText.UpstreamConfigComponent_MergeRadio);
		mergeRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfigSelected();
			}
		});
		mergeRadio.setSelection(true);

		rebaseRadio = new Button(upstreamConfigGroup, SWT.RADIO);
		rebaseRadio.setText(UIText.UpstreamConfigComponent_RebaseRadio);
		rebaseRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				upstreamConfigSelected();
			}
		});
