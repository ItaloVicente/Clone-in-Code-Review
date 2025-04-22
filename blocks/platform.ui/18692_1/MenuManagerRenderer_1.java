		tmp.add(rec);
	}

	public void removeRecord(MMenuElement item, ContributionRecord rec) {
		ArrayList<ContributionRecord> tmp = sharedElementToRecord.get(item);
		if (tmp != null) {
			tmp.remove(rec);
			if (tmp.isEmpty()) {
				sharedElementToRecord.remove(item);
			}
		}
