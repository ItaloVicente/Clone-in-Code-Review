		if (element instanceof MMenuItem) {
			handleLabelOfMenuItem(event, element);
		}
		else if (element instanceof MMenu) {
			updateLabelOfMenu(event);
		}
	}
