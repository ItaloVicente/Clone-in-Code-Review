        saveSelected.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                saveItems(editorsTable.getSelection());
            }
        });
