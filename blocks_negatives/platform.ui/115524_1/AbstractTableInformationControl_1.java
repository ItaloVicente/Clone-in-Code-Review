			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		table.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				gotoSelectedElement();
			}
		});
