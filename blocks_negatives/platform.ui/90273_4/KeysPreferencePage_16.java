		buttonAddKey.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				Point buttonLocation = buttonAddKey.getLocation();
				buttonLocation = groupKeySequence.toDisplay(buttonLocation.x,
						buttonLocation.y);
				Point buttonSize = buttonAddKey.getSize();
				menuButtonAddKey.setLocation(buttonLocation.x, buttonLocation.y
						+ buttonSize.y);
				menuButtonAddKey.setVisible(true);
			}
		});
