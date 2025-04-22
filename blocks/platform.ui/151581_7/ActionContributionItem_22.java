		if (widget instanceof Button) {
			Button button = (Button) widget;

			if (imageChanged) {
				updateImages(false);
			}

			if (textChanged) {
				String text = action.getText();
				boolean showText = text != null && ((getMode() & MODE_FORCE_TEXT) != 0 || !hasImages(action));
				if (showText) {
					text = Action.removeAcceleratorText(text);
