        selectClean.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                editorsTable.setSelection(selectClean(editorsTable.getItems()));
                updateButtons();
            }
        });
