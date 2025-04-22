					IContainer generatedParent = generateContainers(parentPath);
					resource.move(destinationPath, IResource.SHALLOW | IResource.KEEP_HISTORY,
							iterationProgress.split(100));
					if (generatedParent == null) {
						resourcesAtDestination.add(getWorkspace().getRoot()
								.findMember(destinationPath));
					} else {
						resourcesAtDestination.add(generatedParent);
					}
