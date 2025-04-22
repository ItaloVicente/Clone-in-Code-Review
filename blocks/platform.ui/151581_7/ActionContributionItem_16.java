		boolean textChanged = propertyName == null || propertyName.equals(IAction.TEXT);
		boolean imageChanged = propertyName == null || propertyName.equals(IAction.IMAGE);
		boolean tooltipTextChanged = propertyName == null || propertyName.equals(IAction.TOOL_TIP_TEXT);
		boolean enableStateChanged = propertyName == null || propertyName.equals(IAction.ENABLED)
				|| propertyName.equals(IContributionManagerOverrides.P_ENABLED);
		boolean checkChanged = (action.getStyle() == IAction.AS_CHECK_BOX
				|| action.getStyle() == IAction.AS_RADIO_BUTTON)
				&& (propertyName == null || propertyName.equals(IAction.CHECKED));
