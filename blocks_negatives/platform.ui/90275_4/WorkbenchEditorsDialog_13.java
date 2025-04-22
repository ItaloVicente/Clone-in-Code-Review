        allSelection.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                editorsTable.setSelection(editorsTable.getItems());
                updateButtons();
            }
        });
