            changeButton.addSelectionListener(new SelectionAdapter() {
                @Override
				public void widgetSelected(SelectionEvent evt) {
                    String newValue = changePressed();
                    if (newValue != null) {
                        setStringValue(newValue);
                    }
                }
            });
