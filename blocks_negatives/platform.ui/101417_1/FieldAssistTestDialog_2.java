		dec.addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent event) {
				if (comboField.isValid()) {
					return;
				}
				if (comboField.quickFixMenu == null) {
					comboField.quickFixMenu = createQuickFixMenu(comboField);
				}
				comboField.quickFixMenu.setLocation(event.x, event.y);
				comboField.quickFixMenu.setVisible(true);
