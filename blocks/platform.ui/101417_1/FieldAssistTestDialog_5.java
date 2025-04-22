		dec.addMenuDetectListener(event -> {
			if (comboField.isValid()) {
				return;
			}
			if (comboField.quickFixMenu == null) {
				comboField.quickFixMenu = createQuickFixMenu(comboField);
