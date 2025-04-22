	private static class NeedEntry {
		private final GitSynchronizeData gsd;

		private Set<String> paths;

		public NeedEntry(GitSynchronizeData gsd) {
			this.gsd = gsd;
		}

		boolean apply(String pathString) {
			if (gsd == null) {
				return true;
			}
			if (paths == null) {
				initPaths();
			}
			return paths.contains(pathString);
		}

		private void initPaths() {
			Set<IResource> resources = gsd.getIncludedResources();
			if (resources != null && !resources.isEmpty()) {
				paths = new HashSet<String>(resources.size());
				final Path repositoryPath = new Path(gsd.getRepository()
						.getWorkTree().getAbsolutePath());
				for (IResource resource : gsd.getIncludedResources()) {
					IPath resourceLocation = resource.getLocation();
					if (resourceLocation != null) {
						paths.add(resourceLocation.makeRelativeTo(
								repositoryPath).toString());
					}
				}
			} else {
				paths = Collections.emptySet();
			}
		}
	}

