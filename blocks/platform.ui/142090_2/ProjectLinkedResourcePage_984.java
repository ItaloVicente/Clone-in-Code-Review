		return pathVariablesGroup.performOk() && linkedResourceEditor.performOk();
	}

	protected void updateWidgetState(boolean enableLinking) {
		topLabel.setEnabled(enableLinking);
		pathVariablesGroup.setEnabled(enableLinking);
		linkedResourceEditor.setEnabled(enableLinking);
	}
