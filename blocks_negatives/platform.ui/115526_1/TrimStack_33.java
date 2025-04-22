		restoreBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				minimizedElement.getTags().remove(IPresentationEngine.MINIMIZED);
			}
		});
