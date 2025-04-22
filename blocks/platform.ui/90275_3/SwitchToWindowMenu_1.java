                    mi.addSelectionListener(widgetSelectedAdapter(e -> {
					    Shell windowShell = window.getShell();
					    if (windowShell.getMinimized()) {
							windowShell.setMinimized(false);
						}
					    windowShell.setActive();
					    windowShell.moveAbove(null);
					}));
