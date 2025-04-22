				try (Git git = new Git(repository)) {
					progress.worked(1);
					MergeCommand merge = git.merge().setProgressMonitor(
							new EclipseGitProgressTransformer(
									progress.newChild(1)));
					Ref ref = repository.findRef(refName);
					if (ref != null) {
