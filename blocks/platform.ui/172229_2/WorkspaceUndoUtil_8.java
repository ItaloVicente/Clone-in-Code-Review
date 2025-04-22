					resource.move(destinationPath, IResource.SHALLOW
							| IResource.KEEP_HISTORY,
							iterationProgress.split(99));
					resourcesAtDestination.add(getWorkspace().getRoot()
							.findMember(destinationPath));
					overwrittenResources.addAll(Arrays.asList(deleted));
				}
			} else {
				reverseDestinations.add(resource.getFullPath());
				IPath parentPath = destination;
				if (pathIncludesName) {
					parentPath = destination.removeLastSegments(1);
				}
