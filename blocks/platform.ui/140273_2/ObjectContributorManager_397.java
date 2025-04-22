		for (Object object : objects) {
			if (object instanceof ContributorRecord) {
				ContributorRecord contributorRecord = (ContributorRecord) object;
				unregisterContributor((contributorRecord).contributor, (contributorRecord).objectClassName);
				contributorRecordSet.remove(contributorRecord);
			}
		}
	}

	public void dispose() {
		if (getExtensionPointFilter() != null) {
