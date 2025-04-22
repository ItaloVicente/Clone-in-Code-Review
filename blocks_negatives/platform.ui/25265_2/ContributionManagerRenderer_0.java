	@SuppressWarnings("unchecked")
	public ContrRec getContributionRecord(MUIElement element) {
		return (ContrRec) element.getTransientData().get(
				CONTRIBUTION_RECORD_FOR_MODEL);
	}

	protected void removeContributionRecord(MUIElement element) {
		element.getTransientData().remove(CONTRIBUTION_RECORD_FOR_MODEL);
