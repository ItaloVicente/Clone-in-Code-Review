			Button button = new Button(shell, SWT.PUSH);
			button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
			button.setText("Print to console");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println(person);
				}
			});
