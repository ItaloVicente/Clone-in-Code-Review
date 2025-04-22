				if (((Button) event.widget).getSelection()) {
					mergeSquash = false;
					mergeCommit = false;
				}
			}
		});

		Button noCommit = new Button(mergeTypeGroup, SWT.RADIO);
		noCommit.setText(UIText.MergeTargetSelectionDialog_MergeTypeSquashButton);
		noCommit.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (((Button) event.widget).getSelection()) {
