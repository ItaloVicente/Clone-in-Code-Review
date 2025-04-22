	private static ArrayList<ContributionRecord> DEFAULT = new ArrayList<ContributionRecord>();

	public ArrayList<ContributionRecord> getList(MMenuElement item) {
		ArrayList<ContributionRecord> tmp = sharedElementToRecord.get(item);
		if (tmp == null) {
			tmp = DEFAULT;
		}
		return tmp;
	}

	public void addRecord(MMenuElement item, ContributionRecord rec) {
		ArrayList<ContributionRecord> tmp = sharedElementToRecord.get(item);
		if (tmp == null) {
			tmp = new ArrayList<ContributionRecord>();
			sharedElementToRecord.put(item, tmp);
		}
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
	}

