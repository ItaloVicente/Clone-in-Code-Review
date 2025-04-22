
		Button b = new Button(shell,SWT.PUSH);
		b.addSelectionListener(new SelectionAdapter() {
			boolean b = true;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if( b ) {
					v.setFilters(new ViewerFilter[] {filter});
					b = false;
				} else {
					v.setFilters(new ViewerFilter[0]);
					b = true;
				}
			}

		});
