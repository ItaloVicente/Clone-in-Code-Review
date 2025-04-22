
	@SuppressWarnings("boxing")
	private int mapButtonLabelToButtonID(String buttonLabel, int defaultId) {


		if (buttonLabelToIdMap != null && buttonLabelToIdMap.containsKey(buttonLabel)) {
			return buttonLabelToIdMap.get(buttonLabel);
		}

