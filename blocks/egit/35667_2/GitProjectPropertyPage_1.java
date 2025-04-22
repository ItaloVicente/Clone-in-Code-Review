
					if (resource != null) {
						IPath location = resource.getLocation();
						if (location != null) {
							String path = mapping.getRepoRelativePath(location);
							if (path != null)
								createAttributesTables(composite, repository,
										path);
						}
					}
