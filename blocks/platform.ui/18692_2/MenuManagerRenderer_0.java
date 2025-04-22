		ArrayList<ContributionRecord> tmp = sharedElementToRecord.get(item);
		if (tmp == null) {
			tmp = DEFAULT;
		}
		return tmp;
	}

	public void addRecord(MMenuElement item, ContributionRecord rec) {
