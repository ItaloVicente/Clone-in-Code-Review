        closeSelected.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                closeItems(editorsTable.getSelection());
            }
        });
