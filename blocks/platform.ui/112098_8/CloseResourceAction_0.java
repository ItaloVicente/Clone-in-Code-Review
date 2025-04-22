	protected CloseResourceAction(IShellProvider provider, String text, String tooltip, String textPlural,
			String tooltipPlural) {
		super(provider, text);
		defaultText = text;
		defaultToolTip = tooltip;
		pluralText = textPlural;
		pluralTooltip = tooltipPlural;
	}

