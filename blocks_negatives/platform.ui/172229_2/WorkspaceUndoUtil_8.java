			} else {
				if (existing != null) {
					if (resource.isLinked() == existing.isLinked()) {
						reverseDestinations.add(resource.getFullPath());
						overwrittenResources.add(copyOverExistingResource(resource, existing,
								iterationProgress.split(100), uiInfo, true));
						resourcesAtDestination.add(existing);
					} else {
						ResourceDescription[] deleted = delete(
								new IResource[] { existing },
								iterationProgress.split(1), uiInfo,
								false);
						reverseDestinations.add(resource.getFullPath());
						resource.move(destinationPath, IResource.SHALLOW
								| IResource.KEEP_HISTORY,
								iterationProgress.split(99));
						resourcesAtDestination.add(getWorkspace().getRoot()
								.findMember(destinationPath));
						overwrittenResources.addAll(Arrays.asList(deleted));
					}
