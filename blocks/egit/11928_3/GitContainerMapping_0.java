		Set<ResourceTraversal> result = new LinkedHashSet<ResourceTraversal>();

		final GitSynchronizeDataSet dataSet;
		if (context instanceof GitSubscriberResourceMappingContext)
			dataSet = ((GitSubscriberResourceMappingContext) context)
					.getSyncData();
		else
			dataSet = null;
