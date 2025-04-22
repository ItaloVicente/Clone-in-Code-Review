			} else if (existing != null) {
				if (resource.isLinked() == existing.isLinked()) {
					reverseDestinations.add(resource.getFullPath());
					overwrittenResources.add(copyOverExistingResource(resource, existing,
							iterationProgress.split(100), uiInfo, true));
					resourcesAtDestination.add(existing);
