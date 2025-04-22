		assertFiresPendingValueChange(new Runnable() {
			@Override
			public void run() {
				shell.notifyListeners(SWT.FocusOut, new Event());
			}
		});
