	private PerspectiveLabelProvider labelProvider = null;

	private PerspectiveLabelProvider getPerspectiveLabelProvider() {
		if(labelProvider==null){
			labelProvider = new PerspectiveLabelProvider(false);
		}
		return labelProvider;
	}


