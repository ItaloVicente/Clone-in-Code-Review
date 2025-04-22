	public SWTBotStyledText getAncestorEditor() {
		List<StyledText> texts = editor.bot().getFinder()
				.findControls(widgetOfType(StyledText.class));
		if (texts.size() < 3) {
			List<ToolItem> toolitems = editor.bot().getFinder()
					.findControls(withTooltip("Show Ancestor Pane"));
			if (toolitems != null && !toolitems.isEmpty()) {
				new SWTBotToolbarToggleButton(toolitems.get(0)).click();
			}
			texts = editor.bot().getFinder()
					.findControls(widgetOfType(StyledText.class));
			if (texts.size() < 3) {
				return null;
			}
		}
		return new SWTBotStyledText(texts.get(0));
	}

