			Object adapted = getResourceMapping(element);
			if (adapted != null && adapted instanceof ResourceMapping) {
				ResourceMapping mapping = (ResourceMapping) adapted;

				if (isMappedToGitProvider(mapping))
					providerMappings.add(mapping);
			}
