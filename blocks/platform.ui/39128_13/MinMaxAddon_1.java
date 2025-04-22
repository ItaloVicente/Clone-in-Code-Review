	private String getTrimId(MUIElement element, MWindow window) {
		String trimId;
		if (MinMaxAddonUtil.isPartOfMinMaxChildrenArea(element)) {
			trimId = TrimStackIdHelper.createTrimStackId(element, null, window);
		} else {
			trimId = TrimStackIdHelper.createTrimStackId(element, modelService.getPerspectiveFor(element), window);
		}
		return trimId;
	}



