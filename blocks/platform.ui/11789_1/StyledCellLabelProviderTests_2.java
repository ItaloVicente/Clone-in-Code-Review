		SelectionAdapter adapter = new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				if (((Button)e.getSource()).getSelection()) {
					column.getColumn().dispose();
					int style = e.getSource() == leftButton ? SWT.LEFT : (e.getSource() == centerButton ? SWT.CENTER : SWT.RIGHT);
					createColumn(tableViewer, style, labelProvider);
				}
			}
		}; 
		leftButton.addSelectionListener(adapter);
		centerButton.addSelectionListener(adapter);
		rightButton.addSelectionListener(adapter);

