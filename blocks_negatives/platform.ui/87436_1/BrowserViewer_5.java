        combo.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent we) {
                try {
                    if (combo.getSelectionIndex() != -1 && !combo.getListVisible()) {
                        setURL(combo.getItem(combo.getSelectionIndex()));
                    }
                } catch (Exception e) {
                }
            }
        });
