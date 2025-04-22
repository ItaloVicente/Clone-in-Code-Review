						IFolder folder = workspaceRoot
								.getFolder(destinationPath);
						if (createVirtual) {
							folder.create(IResource.VIRTUAL, true, iterationProgress.split(1));
							IResource[] members = ((IContainer) source).members();
							if (members.length > 0) {
								overwrittenResources.addAll(Arrays.asList(copy(members, destinationPath,
										resourcesAtDestination, iterationProgress.split(99), uiInfo, false,
										createVirtual, createLinks, relativeToVariable)));
							}
						} else
							folder.createLink(
									createRelativePath(source.getLocationURI(), relativeToVariable, folder), 0,
									iterationProgress.split(100));
