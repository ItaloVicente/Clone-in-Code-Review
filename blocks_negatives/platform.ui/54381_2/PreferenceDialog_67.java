		BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
			@Override
			public void run() {
				control[0] = PreferenceDialog.super.createContents(parent);
				selectSavedItem();
			}
