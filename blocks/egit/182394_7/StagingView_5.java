			ScrollBar bar = tree.getHorizontalBar();
			if (bar != null) {
				bar.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						super.widgetSelected(e);
						tree.getDisplay().asyncExec(() -> {
							if (!tree.isDisposed()) {
								tree.redraw();
							}
						});
					}
				});
			}
