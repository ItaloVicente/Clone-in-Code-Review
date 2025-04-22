        showAllPerspButton.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                showAllPersp = showAllPerspButton.getSelection();
                updateItems();
            }
        });
