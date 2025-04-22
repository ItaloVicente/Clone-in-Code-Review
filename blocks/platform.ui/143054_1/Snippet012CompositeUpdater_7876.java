			Button button = new Button(shell, SWT.PUSH);
			button.setText("add");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					list.add(0, new Counter());
				}
			});
