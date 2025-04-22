
	private String getMinimizedElementSuffix(MUIElement element) {
		String id = ID_SUFFIX;
		MPerspective persp = modelService.getPerspectiveFor(element);
		if (persp != null) {
			id = '(' + persp.getElementId() + ')';
		}
		return id;
	}
