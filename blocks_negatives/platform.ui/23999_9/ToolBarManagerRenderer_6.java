		return contributionToModel.get(item);
	}

	public void linkModelToContribution(MToolBarElement model,
			IContributionItem item) {
		modelToContribution.put(model, item);
		contributionToModel.put(item, model);
	}

	public void clearModelToContribution(MToolBarElement model,
			IContributionItem item) {
		modelToContribution.remove(model);
		contributionToModel.remove(item);
	}

	public ArrayList<ToolBarContributionRecord> getList(MToolBarElement item) {
		ArrayList<ToolBarContributionRecord> tmp = sharedElementToRecord
				.get(item);
		if (tmp == null) {
			tmp = new ArrayList<ToolBarContributionRecord>();
			sharedElementToRecord.put(item, tmp);
		}
		return tmp;
	}

	public void linkElementToContributionRecord(MToolBarElement element,
			ToolBarContributionRecord record) {
		modelContributionToRecord.put(element, record);
	}

	public ToolBarContributionRecord getContributionRecord(
			MToolBarElement element) {
		return modelContributionToRecord.get(element);
