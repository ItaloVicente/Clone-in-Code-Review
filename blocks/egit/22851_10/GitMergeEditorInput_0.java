					final IPath projectLocation = project.getLocation();
					if (projectLocation.equals(path)) {
						resourcesInOperation
								.addAll(getConflictingFilesFrom(project));
						foundMatchInWS = true;
					} else if (project.getLocation().isPrefixOf(path)) {
						final IResource resource = ResourceUtil
								.getResourceForLocation(path);
						if (resource instanceof IContainer)
							resourcesInOperation
									.addAll(getConflictingFilesFrom((IContainer) resource));
						else
							resourcesInOperation.add(resource);
