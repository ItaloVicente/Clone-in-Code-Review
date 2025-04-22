		private void refreshRepository(WorkingTreeModifiedEvent event,
				IProgressMonitor monitor) throws CoreException {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (event.isEmpty()) {
				return; // Should actually not occur
			}
			Set<IPath> roots = getProjectLocations(event.getRepository());
			if (roots.isEmpty()) {
				return;
			}
			SubMonitor progress = SubMonitor.convert(monitor, 2);
			IPath workTree = new Path(
					event.getRepository().getWorkTree().getAbsolutePath());
			Map<IResource, Boolean> toRefresh = computeResources(event,
					workTree, roots, progress.newChild(1));
