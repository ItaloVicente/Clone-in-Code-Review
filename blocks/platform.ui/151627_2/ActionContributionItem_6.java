			String textToSet = showText ? text : ""; //$NON-NLS-1$
			button.setText(textToSet);
		}

		if (tooltipTextChanged) {
			button.setToolTipText(action.getToolTipText());
		}
