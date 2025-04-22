			if (activeEditor != null) {
				return;
			}
			if (event.getProperty().equals(IAction.ENABLED)) {
				Boolean bool = (Boolean) event.getNewValue();
				actionHandler.setEnabled(bool.booleanValue());
				return;
			}
			if (event.getProperty().equals(IAction.TEXT)) {
				String text = (String) event.getNewValue();
				actionHandler.setText(text);
				return;
			}
			if (event.getProperty().equals(IAction.TOOL_TIP_TEXT)) {
				String text = (String) event.getNewValue();
				actionHandler.setToolTipText(text);
				return;
			}
		}
	}

	private class CellChangeListener implements IPropertyChangeListener {
		@Override
