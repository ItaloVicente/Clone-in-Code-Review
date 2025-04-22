		final Button useMerged = new Button(main, SWT.RADIO);
		useMerged.setText(UIText.MergeModeDialog_MergeMode_3_Label);
		useMerged.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mode = MergeInputMode.MERGED_OURS;
			}
		});
		useMerged.setSelection(true);
