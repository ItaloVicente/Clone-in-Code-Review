			} else {
				if (existing != null) {
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
