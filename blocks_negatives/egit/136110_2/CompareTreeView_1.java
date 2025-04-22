				List<TreeFilter> orFilters = new ArrayList<>(
						resources.length);

				for (IResource resource : resources) {
					String relPath = repositoryMapping
							.getRepoRelativePath(resource);
					if (relPath != null && relPath.length() > 0) {
						orFilters.add(PathFilter.create(relPath));
					}
				}
