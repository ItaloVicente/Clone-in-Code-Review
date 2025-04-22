		creationDstComboSupport = new ComboLabelingSupport(creationDstCombo,
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						tryAutoCompleteDstToSrc();
					}
				});
