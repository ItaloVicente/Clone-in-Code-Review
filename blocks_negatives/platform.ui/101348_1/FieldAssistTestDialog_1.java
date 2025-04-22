		dec.addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent event) {
				if (textField.isValid()) {
					return;
				}
				if (textField.quickFixMenu == null) {
					textField.quickFixMenu = createQuickFixMenu(textField);
				}
				textField.quickFixMenu.setLocation(event.x, event.y);
				textField.quickFixMenu.setVisible(true);
