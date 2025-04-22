						iterationProgress.setWorkRemaining(100);

						if ((createLinks || createVirtualFoldersAndLinks) && (source.isLinked() == false)
								&& (source.isVirtual() == false)) {
							if (source.getType() == IResource.FILE) {
								IFile file = workspaceRoot.getFile(destinationPath);
								file.createLink(createRelativePath(source.getLocationURI(), file), 0,
										iterationProgress.newChild(100));
							} else {
								IFolder folder = workspaceRoot.getFolder(destinationPath);
								if (createVirtualFoldersAndLinks) {
									folder.create(IResource.VIRTUAL, true, subMonitor.newChild(1));
									IResource[] members = ((IContainer) source).members();
									if (members.length > 0)
										copy(members, destinationPath, iterationProgress.newChild(100));
								} else
									folder.createLink(createRelativePath(source.getLocationURI(), folder), 0,
											iterationProgress.newChild(100));
							}
						} else
							source.copy(destinationPath, IResource.SHALLOW, iterationProgress.newChild(100));
