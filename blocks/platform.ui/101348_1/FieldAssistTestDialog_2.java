		dec.addMenuDetectListener(event -> {
			if (textField.isValid()) {
				return;
			}
			if (textField.quickFixMenu == null) {
				textField.quickFixMenu = createQuickFixMenu(textField);
