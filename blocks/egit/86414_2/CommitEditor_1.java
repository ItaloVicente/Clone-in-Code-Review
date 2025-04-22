		IFormPage currentPage = getActivePageInstance();
		IShowInSource showInSource = AdapterUtils.adapt(currentPage,
				IShowInSource.class);
		if (showInSource != null) {
			return showInSource.getShowInContext();
		}
		return null;
	}

	@Override
	public String[] getShowInTargetIds() {
		IFormPage currentPage = getActivePageInstance();
		IShowInTargetList targetList = AdapterUtils.adapt(currentPage,
				IShowInTargetList.class);
		if (targetList != null) {
			return targetList.getShowInTargetIds();
		}
		return null;
