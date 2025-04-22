		creationSrcComboSupport = new ComboLabelingSupport(creationSrcCombo,
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						tryAutoCompleteSrcToDst();
					}
				});
