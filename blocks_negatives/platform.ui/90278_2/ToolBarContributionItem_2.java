                coolItem.addSelectionListener(new SelectionAdapter() {

                    @Override
					public void widgetSelected(SelectionEvent event) {
                        if (event.detail == SWT.ARROW) {
                            handleChevron(event);
                        }
                    }
                });
