
		Iterator<Entry<MMenuElement, ContributionRecord>> iterator = modelContributionToRecord
				.entrySet().iterator();
		for (; iterator.hasNext();) {
			Entry<MMenuElement, ContributionRecord> entry = iterator.next();
			ContributionRecord record = entry.getValue();
			if (disposedRecords.contains(record))
				iterator.remove();
		}
