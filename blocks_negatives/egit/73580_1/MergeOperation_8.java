				Git git = new Git(repository);
				progress.worked(1);
				MergeCommand merge = git.merge()
						.setProgressMonitor(
								new EclipseGitProgressTransformer(progress.newChild(1)));
				try {
					Ref ref = repository.getRef(refName);
					if (ref != null)
