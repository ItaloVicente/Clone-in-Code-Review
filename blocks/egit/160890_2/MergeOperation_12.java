		IWorkspaceRunnable action = mymonitor -> {
			IProject[] validProjects = ProjectUtil.getValidOpenProjects(repository);
			SubMonitor progress = SubMonitor.convert(mymonitor, NLS.bind(
					CoreText.MergeOperation_ProgressMerge, refName), 3);
			try (Git git = new Git(repository)) {
				progress.worked(1);
				MergeCommand merge = git.merge().setProgressMonitor(
						new EclipseGitProgressTransformer(
								progress.newChild(1)));
				Ref ref = repository.findRef(refName);
				if (ref != null) {
					merge.include(ref);
				} else {
					merge.include(ObjectId.fromString(refName));
