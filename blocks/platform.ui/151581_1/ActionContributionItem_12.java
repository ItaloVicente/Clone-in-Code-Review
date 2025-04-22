		if (widget instanceof ToolItem) {
			ToolItem ti = (ToolItem) widget;
			String text = action.getText();
			boolean showText = text != null && ((getMode() & MODE_FORCE_TEXT) != 0 || !hasImages(action));

			if (showText && text != null) {
				text = Action.removeAcceleratorText(text);
				text = Action.removeMnemonics(text);
			}
