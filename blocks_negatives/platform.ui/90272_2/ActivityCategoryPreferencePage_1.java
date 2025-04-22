        disableAll.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
                workingCopy.setEnabledActivityIds(Collections.EMPTY_SET);
            }
        });
