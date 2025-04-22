
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int i = combo.getSelectionIndex();
				if (i != -1 && i < proposals.size()) {
					combo.setText(proposals.get(i).getContent());
					if (selectionListener != null)
						selectionListener.widgetSelected(e);
				}
			}
		});
