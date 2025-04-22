					} else {
						if (source.isLinked() == existing.isLinked()) {
							overwrittenResources.add(copyOverExistingResource(source, existing,
									iterationProgress.split(100), uiInfo, false));
							resourcesAtDestination.add(existing);
						} else {
							ResourceDescription[] deleted = delete(
									new IResource[] { existing },
									iterationProgress.split(1), uiInfo,
									false);
							source.copy(destinationPath, IResource.SHALLOW, iterationProgress.split(99));
							resourcesAtDestination.add(getWorkspace().getRoot()
									.findMember(destinationPath));
							overwrittenResources.addAll(Arrays.asList(deleted));
						}
