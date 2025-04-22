		configsTable.getTable().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.DEL && removeButton.isEnabled()) {
					removeSelectedFilters();
				}
			}
		});

