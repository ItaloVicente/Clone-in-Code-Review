		for (Iterator iter = resolutionQueries.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Entry) iter.next();
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
							for (int i = 0; i < generatedResolutions.length; i++) {
								resolutions.add(generatedResolutions[i]);
							}
						}

