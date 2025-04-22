			public Control setEditor(ViewerCell cell, Composite parent,
					Control existing, Object element) {
				Button editor;
				if (existing != null) {
					editor = (Button) existing;
				} else {
					editor = new Button(parent, SWT.CHECK);
					editor.addSelectionListener(new SelectionAdapter() {

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
							if (!editor.isDisposed()) {
								editor.redraw();
							}
						};
						tableViewer.addSelectionChangedListener(listener);
						editor.addDisposeListener(event -> tableViewer
								.removeSelectionChangedListener(listener));
					}
				}
				boolean isDeletion = isDeleteRefSpec(element);
				editor.setData(element);
				editor.setEnabled(!isDeletion);
				editor.setSelection(
						((RefSpec) element).isForceUpdate() || isDeletion);
				return editor;
