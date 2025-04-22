        go.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent event) {
                setURL(combo.getText());
            }
        });
