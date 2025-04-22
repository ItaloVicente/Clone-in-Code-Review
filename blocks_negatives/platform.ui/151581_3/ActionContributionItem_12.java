		if (widget != null) {
			boolean textChanged = propertyName == null
					|| propertyName.equals(IAction.TEXT);
			boolean imageChanged = propertyName == null
					|| propertyName.equals(IAction.IMAGE);
			boolean tooltipTextChanged = propertyName == null
					|| propertyName.equals(IAction.TOOL_TIP_TEXT);
			boolean enableStateChanged = propertyName == null
					|| propertyName.equals(IAction.ENABLED)
					|| propertyName
							.equals(IContributionManagerOverrides.P_ENABLED);
			boolean checkChanged = (action.getStyle() == IAction.AS_CHECK_BOX || action
					.getStyle() == IAction.AS_RADIO_BUTTON)
					&& (propertyName == null || propertyName
							.equals(IAction.CHECKED));

			if (widget instanceof ToolItem) {
				ToolItem ti = (ToolItem) widget;
				String text = action.getText();
				boolean showText = text != null
						&& ((getMode() & MODE_FORCE_TEXT) != 0 || !hasImages(action));
