			} else if (existing != null) {
				if ((createLinks || createVirtual)
						&& (source.isLinked() == false)) {
					ResourceDescription[] deleted = delete(
							new IResource[] { existing },
							iterationProgress.split(1), uiInfo,
							false);
					iterationProgress.setWorkRemaining(100);
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
					resourcesAtDestination.add(getWorkspace().getRoot()
							.findMember(destinationPath));
					overwrittenResources.addAll(Arrays.asList(deleted));
				} else {
					if (source.isLinked() == existing.isLinked()) {
						overwrittenResources.add(copyOverExistingResource(source, existing,
								iterationProgress.split(100), uiInfo, false));
						resourcesAtDestination.add(existing);
					} else {
