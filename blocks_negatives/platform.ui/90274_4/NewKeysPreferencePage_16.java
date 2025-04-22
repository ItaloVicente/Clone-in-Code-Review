		addKeyButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				Point buttonLocation = addKeyButton.getLocation();
				buttonLocation = dataArea.toDisplay(buttonLocation.x,
						buttonLocation.y);
				Point buttonSize = addKeyButton.getSize();
				addKeyMenu.setLocation(buttonLocation.x, buttonLocation.y
						+ buttonSize.y);
				addKeyMenu.setVisible(true);
			}
		});
