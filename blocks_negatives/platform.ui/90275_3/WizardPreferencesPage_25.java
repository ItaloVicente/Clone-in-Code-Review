		SelectionListener listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.setAllChecked(true);
				updatePageCompletion();
			}
		};
