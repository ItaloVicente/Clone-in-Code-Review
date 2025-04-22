							} else {
								IFolder folder = workspaceRoot.getFolder(destinationPath);
								if (createVirtualFoldersAndLinks) {
									folder.create(IResource.VIRTUAL, true, iterationMonitor.split(1));
									IResource[] members = ((IContainer) resource).members();
									if (members.length > 0)
										copy(members, destinationPath, iterationMonitor.split(99));
								} else
									folder.createLink(createRelativePath(resource.getLocationURI(), folder), 0,
											iterationMonitor.split(100));
							}
						} else
							resource.copy(destinationPath, IResource.SHALLOW, iterationMonitor.split(100));
					}
