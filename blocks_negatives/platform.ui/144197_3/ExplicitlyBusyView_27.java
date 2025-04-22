		{
			Button button = new Button(parent, SWT.PUSH);
			button.setText("Spawn Job");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					new SomeJob("Some Job " + counter++).schedule();
				}
			});
		}
