	private void extractResourceProperties(TreeWalk treeWalk) throws IOException {
		DecoratableResourceHelper.decorateResource(this, treeWalk);
	}

	private class RecursiveStateFilter extends TreeFilter {

		private int filesChecked = 0;

		private int targetDepth = -1;

		private final int recurseLimit;

		public RecursiveStateFilter() {
			recurseLimit = store
					.getInt(UIPreferences.DECORATOR_RECURSIVE_LIMIT);
		}

		@Override
		public boolean include(TreeWalk treeWalk)
				throws MissingObjectException, IncorrectObjectTypeException,
				IOException {
			if (trace)
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.DECORATION.getLocation(),
						treeWalk.getPathString());
			final WorkingTreeIterator workingTreeIterator = treeWalk.getTree(
					DecoratableResourceHelper.T_WORKSPACE,
					WorkingTreeIterator.class);
			if (workingTreeIterator != null) {
				if (workingTreeIterator instanceof ContainerTreeIterator) {
					final ContainerTreeIterator workspaceIterator =
						(ContainerTreeIterator) workingTreeIterator;
					ResourceEntry resourceEntry = workspaceIterator
							.getResourceEntry();
					if (resource.equals(resourceEntry.getResource())
							&& workspaceIterator.isEntryIgnored()) {
						ignored = true;
						return false;
					}
					if (resource.getFullPath().isPrefixOf(
							resourceEntry.getResource().getFullPath())
							&& treeWalk
									.getFileMode(DecoratableResourceHelper.T_HEAD) == FileMode.MISSING
							&& treeWalk
									.getFileMode(DecoratableResourceHelper.T_INDEX) == FileMode.MISSING) {
						if (trace)
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.DECORATION.getLocation(),
						return false;
					}

				} else {
					IPath wdPath = new Path(repository.getWorkTree()
							.getAbsolutePath()).append(workingTreeIterator
							.getEntryPathString());
					IPath resPath = resource.getLocation();
					if (wdPath.equals(resPath)
							&& workingTreeIterator.isEntryIgnored()) {
						ignored = true;
						return false;

					}
					if (resPath.isPrefixOf(wdPath)
							&& treeWalk
									.getFileMode(DecoratableResourceHelper.T_HEAD) == FileMode.MISSING
							&& treeWalk
									.getFileMode(DecoratableResourceHelper.T_INDEX) == FileMode.MISSING) {
						if (trace)
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.DECORATION.getLocation(),
						return false;
					}
				}
			}

			if (FileMode.TREE.equals(treeWalk
					.getRawMode(DecoratableResourceHelper.T_WORKSPACE)))
				return shouldRecurse(treeWalk);

			Staged wasStaged = staged;
			boolean wasDirty = dirty;
			boolean hadConflicts = conflicts;

			extractResourceProperties(treeWalk);
			filesChecked++;

			ignored = false;
			assumeValid = false;
			dirty = wasDirty || dirty;
			conflicts = hadConflicts || conflicts;
			if (staged != wasStaged && filesChecked > 1)
				staged = Staged.MODIFIED;

			return false;
		}

		private boolean shouldRecurse(TreeWalk treeWalk) throws IOException {
			final WorkingTreeIterator workspaceIterator = treeWalk.getTree(
					DecoratableResourceHelper.T_WORKSPACE,
					WorkingTreeIterator.class);

			if (workspaceIterator instanceof AdaptableFileTreeIterator)
				return true;

			ResourceEntry resourceEntry = null;
			if (workspaceIterator != null)
				resourceEntry = ((ContainerTreeIterator) workspaceIterator)
						.getResourceEntry();

			if (resourceEntry == null)
				return true;

			IResource visitingResource = resourceEntry.getResource();
			if (targetDepth == -1) {
				if (visitingResource.equals(resource)
						|| visitingResource.getParent().equals(resource))
					targetDepth = treeWalk.getDepth();
				else
					return true;
			}

			if ((treeWalk.getDepth() - targetDepth) >= recurseLimit) {
				if (visitingResource.equals(resource))
					extractResourceProperties(treeWalk);

				return false;
			}

			return true;
		}
