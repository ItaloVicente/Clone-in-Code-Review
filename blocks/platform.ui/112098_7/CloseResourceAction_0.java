	protected CloseResourceAction(IShellProvider provider, String text, String tooltip, String textPlural,
			String tooltipPlural) {
		super(provider, text);
		textString = text;
		tooltipString = tooltip;
		textPluralString = textPlural;
		tooltipPluralString = tooltipPlural;
	}

