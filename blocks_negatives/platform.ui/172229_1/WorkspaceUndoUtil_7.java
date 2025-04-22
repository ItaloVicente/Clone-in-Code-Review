				} else {
					IPath parentPath = destination;
					if (pathIncludesName) {
						parentPath = destination.removeLastSegments(1);
					}
					IContainer generatedParent = generateContainers(parentPath);
					if ((createLinks || createVirtual)
							&& (source.isLinked() == false)) {
						if (source.getType() == IResource.FILE) {
							IFile file = workspaceRoot.getFile(destinationPath);
							file.createLink(createRelativePath(
									source.getLocationURI(), relativeToVariable, file), 0,
									iterationProgress.split(100));
						} else {
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
						}
					} else
						source.copy(destinationPath, IResource.SHALLOW, iterationProgress.split(100));
					if (generatedParent == null) {
						resourcesAtDestination.add(getWorkspace().getRoot()
								.findMember(destinationPath));
