		} else {
			if (widget instanceof Button) {
				text = ((Button) element).getText();
				if (text != null) {
					widget.setData(CSSSWTConstants.TEXT_KEY, text);
				}
