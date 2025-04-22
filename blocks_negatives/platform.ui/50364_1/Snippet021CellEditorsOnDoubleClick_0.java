		table.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event event) {
				editingSupport.setEnabled(false);
			}

		});
