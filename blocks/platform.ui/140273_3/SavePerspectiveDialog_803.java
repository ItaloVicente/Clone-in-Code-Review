		perspName = text.getText();

		ignoreSelection = true;
		persp = perspReg.findPerspectiveWithLabel(perspName);
		if (persp == null) {
			StructuredSelection sel = new StructuredSelection();
			list.setSelection(sel);
		} else {
			StructuredSelection sel = new StructuredSelection(persp);
			list.setSelection(sel);
		}
		ignoreSelection = false;

		updateButtons();
	}

	@Override
