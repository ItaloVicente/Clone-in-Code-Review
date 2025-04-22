		@Override
		public void update(ViewerCell cell) {
			Object obj = cell.getElement();
			Widget w = cell.getViewerRow().getItem();
			if (w instanceof TableItem) {
				TableItem item = (TableItem) w;
				if (obj instanceof RefSpec) {
					RefSpec r = (RefSpec) obj;
					Button button = buttons.get(item);
					boolean isDeletion = isDeleteRefSpec(r);
					if (button != null) {
						button.setData(r);
						button.setEnabled(!isDeletion);
						button.setSelection(r.isForceUpdate() || isDeletion);
					} else {
						Table table = item.getParent();
						button = new Button(table, SWT.CHECK);
						buttons.put(item, button);
						button.pack();
						button.setData(r);
						button.setEnabled(!isDeletion);
						button.setSelection(r.isForceUpdate() || isDeletion);
						button.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								Button b = (Button) e.getSource();
								RefSpec oldSpec = (RefSpec) b.getData();
								RefSpec newSpec = oldSpec
										.setForceUpdate(b.getSelection());
								setRefSpec(oldSpec, newSpec);
							}
						});
						TableEditor editor = new TableEditor(table);
						editor.grabVertical = true;
						editor.horizontalAlignment = SWT.CENTER;
						editor.verticalAlignment = SWT.CENTER;
						Point size = button.getSize();
						editor.minimumWidth = size.x;
						editor.minimumHeight = size.y;
						editor.setEditor(button, item, index);
						item.addDisposeListener(e -> {
							editor.getEditor().dispose();
							editor.dispose();
							Button b = buttons.remove(item);
							b.dispose();
						});
					}
				}
