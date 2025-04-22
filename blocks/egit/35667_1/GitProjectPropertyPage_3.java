				if (resource != null) {
					String path = repoMapping.getRepoRelativePath(resource
							.getLocation());
					if (path != null)
						createAttributesTables(composite, repository, path);

				}
