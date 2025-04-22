		} else if ("conflictsInSingleRepository".equals(property)) { //$NON-NLS-1$
			IStructuredSelection selection = getStructuredSelection(collection);
			IResource[] resources = SelectionUtils
					.getSelectedResources(selection);
			Repository repository = getRepositoryOfResources(resources);
			if (repository == null
					|| !testRepositoryProperties(repository, args)) {
				return false;
			}
			IndexDiffCacheEntry indexDiff = IndexDiffCache.getInstance()
					.getIndexDiffCacheEntry(repository);
			if (indexDiff == null) {
				return false;
			}
			IndexDiffData data = indexDiff.getIndexDiff();
			if (data == null) {
				return false;
			}
			Set<String> conflicts = data.getConflicting();
			if (conflicts.isEmpty()) {
				return false;
			}
			for (IResource rsc : resources) {
				IFile file = Adapters.adapt(rsc, IFile.class);
				if (file == null) {
					return false;
				}
				IPath location = file.getLocation();
				if (location == null) {
					return false;
				}
				IPath relativePath = ResourceUtil
						.getRepositoryRelativePath(location, repository);
				if (relativePath == null || relativePath.isEmpty()) {
					return false;
				}
				if (!conflicts.contains(relativePath.toString())) {
					return false;
				}
			}
			return true;

