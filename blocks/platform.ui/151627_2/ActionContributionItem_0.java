			updateToolItem((ToolItem) widget, textChanged, imageChanged, tooltipTextChanged, enableStateChanged,
					checkChanged);
		} else if (widget instanceof MenuItem) {
			updateMenuItem((MenuItem) widget, textChanged, imageChanged, tooltipTextChanged, enableStateChanged,
					checkChanged);
		} else if (widget instanceof Button) {
			updateButton((Button) widget, textChanged, imageChanged, tooltipTextChanged, enableStateChanged,
					checkChanged);
		}
	}
