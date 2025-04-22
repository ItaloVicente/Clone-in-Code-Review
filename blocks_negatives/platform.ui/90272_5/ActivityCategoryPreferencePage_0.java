        enableAll.addSelectionListener(new SelectionAdapter() {

            @Override
			public void widgetSelected(SelectionEvent e) {
                workingCopy.setEnabledActivityIds(workingCopy
                        .getDefinedActivityIds());
            }
        });
