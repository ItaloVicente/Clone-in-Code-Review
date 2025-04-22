		private Map<IResource, Boolean> computeResources(
				WorkingTreeModifiedEvent event, IPath workTree,
				Set<IPath> roots, IProgressMonitor monitor) {
			Set<String> modified = event.getModified();
			Set<String> deleted = event.getDeleted();
			if (GitTraceLocation.REPOSITORYCHANGESCANNER.isActive()) {
				GitTraceLocation.getTrace().trace(
						GitTraceLocation.REPOSITORYCHANGESCANNER.getLocation(),
						"Calculating refresh for repository " + workTree + ' ' //$NON-NLS-1$
								+ modified.size() + ' ' + deleted.size());
			}
			SubMonitor progress = SubMonitor.convert(monitor,
					modified.size() + deleted.size());
			Set<IPath> fullRefreshes = new HashSet<>();
			Map<IPath, IFile> handled = new HashMap<>();
			Map<IResource, Boolean> result = new HashMap<>();
			Stream.concat(modified.stream(), deleted.stream()).forEach(path -> {
				if (progress.isCanceled()) {
					throw new OperationCanceledException();
				}
				IPath filePath = workTree.append(path);
				if (fullRefreshes.stream()
						.anyMatch(full -> full.isPrefixOf(filePath))
						|| !roots.stream()
								.anyMatch(root -> root.isPrefixOf(filePath))) {
					progress.worked(1);
					return;
				}
				IPath containerPath = filePath.removeLastSegments(1);
				if (!handled.containsKey(containerPath)) {
					boolean isFile = true;
					String lastPart = filePath.lastSegment();
					while (containerPath != null
							&& workTree.isPrefixOf(containerPath)) {
						IContainer container = ResourceUtil
								.getContainerForLocation(containerPath, false);
						if (container == null) {
							lastPart = containerPath.lastSegment();
							containerPath = containerPath.removeLastSegments(1);
							isFile = false;
							continue;
						}
						if (container.getType() == IResource.ROOT) {
							containerPath = containerPath.append(lastPart);
							fullRefreshes.add(containerPath);
							handled.put(containerPath, null);
						} else if (isFile) {
							IFile file = container.getFile(new Path(lastPart));
							handled.put(containerPath, file);
							result.put(file, Boolean.FALSE);
						} else {
							container = container.getFolder(new Path(lastPart));
							containerPath = containerPath.append(lastPart);
							fullRefreshes.add(containerPath);
							handled.put(containerPath, null);
							result.put(container, Boolean.TRUE);
						}
						break;
