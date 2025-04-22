	protected Map<ModelEl, ContrRec> getModelContributionToRecord() {
		return modelContributionToRecord;
	}

	public List<ContrRec> getList(ModelEl item) {
		ArrayList<ContrRec> tmp = sharedElementToRecord.get(item);
