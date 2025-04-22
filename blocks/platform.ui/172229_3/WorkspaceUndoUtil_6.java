				} else
					source.copy(destinationPath, IResource.SHALLOW, iterationProgress.split(100));
				if (generatedParent == null) {
					resourcesAtDestination.add(getWorkspace().getRoot()
							.findMember(destinationPath));
				} else {
					resourcesAtDestination.add(generatedParent);
