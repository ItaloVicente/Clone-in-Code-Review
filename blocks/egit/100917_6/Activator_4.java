		private void refreshRepository(WorkingTreeChanges changes,
				IProgressMonitor monitor) throws CoreException {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (changes.isEmpty()) {
				return; // Should actually not occur
			}
			Set<IPath> roots = getProjectLocations(changes.getWorkTree());
			if (roots.isEmpty()) {
				return;
			}
			SubMonitor progress = SubMonitor.convert(monitor, 2);
			IPath workTree = new Path(changes.getWorkTree().getPath());
			Map<IResource, Boolean> toRefresh = computeResources(
					changes.getModified(), changes.getDeleted(), workTree,
					roots, progress.newChild(1));
