			public Control setEditor(ViewerCell cell, Composite parent,
					Control existing, Object element) {
				Button button;
				if (existing != null) {
					button = (Button) existing;
				} else {
					button = new Button(parent, SWT.CHECK);
					button.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							Button b = (Button) e.getSource();
							RefSpec oldSpec = (RefSpec) b.getData();
							RefSpec newSpec = oldSpec
									.setForceUpdate(b.getSelection());
							setRefSpec(oldSpec, newSpec);
							tableViewer.setSelection(
									new StructuredSelection(newSpec), false);
						}
					});
					if (Util.isWindows()) {
						ISelectionChangedListener listener = event -> {
							if (!button.isDisposed()) {
								button.redraw();
							}
						};
						tableViewer.addSelectionChangedListener(listener);
						button.addDisposeListener(event -> tableViewer
								.removeSelectionChangedListener(listener));
					}
				}
				boolean isDeletion = isDeleteRefSpec(element);
				button.setData(element);
				button.setEnabled(!isDeletion);
				button.setSelection(
						((RefSpec) element).isForceUpdate() || isDeletion);
				return button;
