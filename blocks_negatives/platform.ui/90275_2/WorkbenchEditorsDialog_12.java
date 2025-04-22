        invertSelection.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                editorsTable.setSelection(invertedSelection(editorsTable
                        .getItems(), editorsTable.getSelection()));
                updateButtons();
            }
        });
