		for (Object element2 : resolutionQueries.entrySet()) {
Map.Entry entry = (Entry) element2;
MarkerQuery query = (MarkerQuery) entry.getKey();
MarkerQueryResult result = query.performQuery(marker);
if (result != null) {
		Map resultsTable = (Map) entry.getValue();

		if (resultsTable.containsKey(result)) {

			Iterator elements = ((Collection) resultsTable.get(result))
					.iterator();
			while (elements.hasNext()) {
				IConfigurationElement element = (IConfigurationElement) elements
						.next();

				IMarkerResolutionGenerator generator = null;
				try {
					generator = (IMarkerResolutionGenerator) element
							.createExecutableExtension(ATT_CLASS);
				} catch (CoreException e) {
					Policy.handle(e);
				}
				if (generator != null) {
					IMarkerResolution[] generatedResolutions = generator
							.getResolutions(marker);
					for (IMarkerResolution generatedResolution : generatedResolutions) {
						resolutions.add(generatedResolution);
