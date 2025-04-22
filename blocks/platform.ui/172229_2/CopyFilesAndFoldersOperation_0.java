			} else if (existing != null) {
				if (homogenousResources(resource, existing)) {
					copyExisting(resource, existing, iterationMonitor.split(100));
				} else {
					delete(existing, iterationMonitor.split(10));
					iterationMonitor.setWorkRemaining(100);

					if ((createLinks || createVirtualFoldersAndLinks) && (resource.isLinked() == false)
							&& (resource.isVirtual() == false)) {
						if (resource.getType() == IResource.FILE) {
							IFile file = workspaceRoot.getFile(destinationPath);
							file.createLink(createRelativePath(resource.getLocationURI(), file), 0,
									iterationMonitor.split(100));
						} else {
							IFolder folder = workspaceRoot.getFolder(destinationPath);
							if (createVirtualFoldersAndLinks) {
								folder.create(IResource.VIRTUAL, true, iterationMonitor.split(1));
								IResource[] members = ((IContainer) resource).members();
								if (members.length > 0)
									copy(members, destinationPath, iterationMonitor.split(99));
							} else
								folder.createLink(createRelativePath(resource.getLocationURI(), folder), 0,
