				final FindToolbarJob finder = createFinder();
				finder.setUser(true);
				finder.schedule(200);
			}
		});

		patternField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail != SWT.ICON_CANCEL
						&& !patternField.getText().isEmpty()) {
					final FindToolbarJob finder = createFinder();
					finder.setUser(true);
					finder.schedule();
				}
