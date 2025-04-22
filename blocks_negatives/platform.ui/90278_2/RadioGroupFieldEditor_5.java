                radio.addSelectionListener(new SelectionAdapter() {
                    @Override
					public void widgetSelected(SelectionEvent event) {
                        String oldValue = value;
                        value = (String) event.widget.getData();
                        setPresentsDefaultValue(false);
                        fireValueChanged(VALUE, oldValue, value);
                    }
                });
