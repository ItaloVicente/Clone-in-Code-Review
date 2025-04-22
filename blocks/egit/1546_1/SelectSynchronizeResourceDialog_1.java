		shouldIncludeLocalButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean includeLocal = shouldIncludeLocalButton.getSelection();
				srcRefCombo.setEnabled(!includeLocal);
				if (includeLocal)
					srcRefCombo.setDefautlValue(
							UIText.SynchronizeWithAction_localRepoName, HEAD);
			}
		});
