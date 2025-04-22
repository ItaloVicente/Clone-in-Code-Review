	private void updateToolItem(ToolItem ti, boolean textChanged, boolean imageChanged, boolean tooltipTextChanged,
			boolean enableStateChanged, boolean checkChanged) {
		String text = action.getText();
		boolean showText = text != null && ((getMode() & MODE_FORCE_TEXT) != 0 || !hasImages(action));

		if (showText && text != null) {
			text = Action.removeAcceleratorText(text);
			text = Action.removeMnemonics(text);
		}
