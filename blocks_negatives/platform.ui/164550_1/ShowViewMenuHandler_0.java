		}

		Display display = menu.getDisplay();
		Point location = display.map(partContainer, null, partContainer.getLocation());
		Point size = partContainer.getSize();
		menu.setLocation(location.x + size.x, location.y);
		menu.setVisible(true);
