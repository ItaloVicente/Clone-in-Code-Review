        link.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
				BusyIndicator.showWhile(link.getDisplay(), () -> doOpenExternal());
            }
        });
