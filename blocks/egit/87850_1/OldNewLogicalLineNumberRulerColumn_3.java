		menuListener = (event) -> {
			if (event.type == SWT.MenuDetect) {
				Menu contextMenu = parentControl.getMenu();
				if (contextMenu != null) {
					contextMenu.setLocation(event.x, event.y);
					contextMenu.setVisible(true);
				}
			}
		};
