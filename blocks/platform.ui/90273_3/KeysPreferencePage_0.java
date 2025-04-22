		buttonAddKey.addSelectionListener(widgetSelectedAdapter(selectionEvent -> {
			Point buttonLocation = buttonAddKey.getLocation();
			buttonLocation = groupKeySequence.toDisplay(buttonLocation.x, buttonLocation.y);
			Point buttonSize = buttonAddKey.getSize();
			menuButtonAddKey.setLocation(buttonLocation.x, buttonLocation.y + buttonSize.y);
			menuButtonAddKey.setVisible(true);
		}));
