		ILabelDecorator currentDecorator = getLabelDecorator();
		String oldText = settings.getText();
		boolean decorationReady = true;
		if (currentDecorator instanceof IDelayedLabelDecorator) {
			IDelayedLabelDecorator delayedDecorator = (IDelayedLabelDecorator) currentDecorator;
			if (!delayedDecorator.prepareDecoration(element, oldText)) {
				decorationReady = false;
			}
		}

		if (decorationReady || oldText == null
				|| settings.getText().length() == 0) {
