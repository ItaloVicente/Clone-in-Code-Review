					if (existing != null) {
						delete(existing, new SubProgressMonitor(subMonitor, 0));
					}

					if ((createLinks || createVirtualFoldersAndLinks)
							&& (source.isLinked() == false)
							&& (source.isVirtual() == false)) {
						if (source.getType() == IResource.FILE) {
							IFile file = workspaceRoot.getFile(destinationPath);
							file.createLink(createRelativePath(source.getLocationURI(), file), 0,
									new SubProgressMonitor(subMonitor, 1));
						} else {
							IFolder folder = workspaceRoot
									.getFolder(destinationPath);
							if (createVirtualFoldersAndLinks) {
									folder.create(IResource.VIRTUAL, true,
											new SubProgressMonitor(subMonitor,
													1));
								IResource[] members = ((IContainer) source)
										.members();
								if (members.length > 0)
									copy(members, destinationPath,
											new SubProgressMonitor(subMonitor,
													1));
							} else
								folder.createLink(createRelativePath(source.getLocationURI(), folder), 0,
								new SubProgressMonitor(subMonitor, 1));
