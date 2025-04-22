		}
		return true;
	}

	public void registerContributor(IObjectContributor contributor, String targetType) {
		List contributorList = (List) contributors.get(targetType);
		if (contributorList == null) {
			contributorList = new ArrayList(5);
			contributors.put(targetType, contributorList);
		}
		contributorList.add(contributor);
		flushLookup();

		IConfigurationElement element = Adapters.adapt(contributor, IConfigurationElement.class);

		if (element != null) {
			ContributorRecord contributorRecord = new ContributorRecord(contributor, targetType);
