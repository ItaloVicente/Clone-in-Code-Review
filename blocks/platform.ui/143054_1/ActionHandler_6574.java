	private Map getAttributeValuesByNameFromAction() {
		Map map = new HashMap();
		map.put(ATTRIBUTE_CHECKED, action.isChecked() ? Boolean.TRUE : Boolean.FALSE);
		map.put(ATTRIBUTE_ENABLED, action.isEnabled() ? Boolean.TRUE : Boolean.FALSE);
		boolean handled = true;
		if (action instanceof RetargetAction) {
			RetargetAction retargetAction = (RetargetAction) action;
			handled = retargetAction.getActionHandler() != null;
		}
		map.put(ATTRIBUTE_HANDLED, handled ? Boolean.TRUE : Boolean.FALSE);
		map.put(ATTRIBUTE_ID, action.getId());
