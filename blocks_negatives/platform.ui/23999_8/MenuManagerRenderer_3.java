		return contributionToModel.get(item);
	}

	public void linkModelToContribution(MMenuElement model,
			IContributionItem item) {
		modelToContribution.put(model, item);
		contributionToModel.put(item, model);
	}

	public void clearModelToContribution(MMenuElement model,
			IContributionItem item) {
		modelToContribution.remove(model);
		contributionToModel.remove(item);
	}

	public ContributionRecord getContributionRecord(MMenuElement element) {
		return modelContributionToRecord.get(element);
	}

	public void linkElementToContributionRecord(MMenuElement element,
			ContributionRecord record) {
		modelContributionToRecord.put(element, record);
	}

	/**
	 * Search the records for testing. Look, but don't touch!
	 * 
	 * @return the array of active ContributionRecords.
	 */
	public ContributionRecord[] getContributionRecords() {
		HashSet<ContributionRecord> records = new HashSet<ContributionRecord>(
				modelContributionToRecord.values());
		return records.toArray(new ContributionRecord[records.size()]);
	}

	@Override
	public IEclipseContext getContext(MUIElement el) {
		return super.getContext(el);
