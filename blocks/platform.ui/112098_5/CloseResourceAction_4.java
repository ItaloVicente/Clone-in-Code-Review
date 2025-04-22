	protected CloseResourceAction(IShellProvider provider, String text, String tooltip, String textPlural,
			String tooltipPlural) {
		super(provider, text);
		TEXT_STRING = text;
		TOOLTIP_STRING = tooltip;
		TEXT_PLURAL_STRING = textPlural;
		TOOLTIP_PLURAL_STRING = tooltipPlural;
	}

