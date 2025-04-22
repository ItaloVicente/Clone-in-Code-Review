			final IPropertyChangeListener propertyListener = event -> {
				String property = event.getProperty();
				if (property.equals(IAction.ENABLED)) {
					toolItem.setEnabled(action.isEnabled());
				} else if (property.equals(IAction.CHECKED)) {
					toolItem.setSelected(action.isChecked());
				} else if (property.equals(IAction.TEXT)) {
					toolItem.setLabel(action.getText());
				} else if (property.equals(IAction.TOOL_TIP_TEXT)) {
					toolItem.setLabel(action.getToolTipText());
