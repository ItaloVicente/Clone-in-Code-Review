		return pathVariablesGroup.performOk();
	}

	protected void updateWidgetState(boolean enableLinking) {
		topLabel.setEnabled(enableLinking);
		pathVariablesGroup.setEnabled(enableLinking);
		dragAndDropHandlingEditor.setEnabled(enableLinking);
	}
