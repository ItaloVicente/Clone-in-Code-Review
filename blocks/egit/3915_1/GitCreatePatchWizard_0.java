			gitFormat.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateEnablement();
				}
			});

			Label spacer= new Label(composite, SWT.NONE);
			GridDataFactory.swtDefaults().hint(50, SWT.DEFAULT).applyTo(spacer);
