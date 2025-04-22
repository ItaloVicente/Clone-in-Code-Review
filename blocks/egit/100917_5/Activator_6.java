		private Set<IPath> getProjectLocations(File workTree) {
			IProject[] projects = RuleUtil.getProjects(workTree);
			if (projects == null) {
				return Collections.emptySet();
			}
			Set<IPath> result = new HashSet<>();
			for (IProject project : projects) {
				if (project.isAccessible()) {
					IPath path = project.getLocation();
					if (path != null) {
						result.add(path);
					}
				}
			}
			return result;
		}

		private Map<IResource, Boolean> computeResources(
				Set<String> modified, Set<String> deleted, IPath workTree,
				Set<IPath> roots, IProgressMonitor monitor) {
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
				IPath filePath = "/".equals(path) ? workTree //$NON-NLS-1$
						: workTree.append(path);
				if (fullRefreshes.stream()
						.anyMatch(full -> full.isPrefixOf(filePath))
						|| !roots.stream()
								.anyMatch(root -> root.isPrefixOf(filePath))) {
					progress.worked(1);
					return;
				}
				IPath containerPath;
				boolean isFile;
				if (path.endsWith("/")) { //$NON-NLS-1$
					isFile = false;
					containerPath = filePath.removeTrailingSeparator();
				} else {
					isFile = true;
					containerPath = filePath.removeLastSegments(1);
				}
				if (!handled.containsKey(containerPath)) {
					if (!isFile && containerPath != null) {
						IContainer container = ResourceUtil
								.getContainerForLocation(containerPath, false);
						if (container != null) {
							IFile file = handled.get(containerPath);
							handled.put(containerPath, null);
							if (file != null) {
								result.remove(file);
