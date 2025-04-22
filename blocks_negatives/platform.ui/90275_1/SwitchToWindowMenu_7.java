                    mi.addSelectionListener(new SelectionAdapter() {
                        @Override
						public void widgetSelected(SelectionEvent e) {
                            Shell windowShell = window.getShell();
                            if (windowShell.getMinimized()) {
								windowShell.setMinimized(false);
							}
                            windowShell.setActive();
                            windowShell.moveAbove(null);
                        }
                    });
