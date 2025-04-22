			IWorkbenchPart part = getPart(event);
			if (part instanceof RebaseInteractiveView) {
				Collections.sort(commits,
						Comparator.<IRepositoryCommit> comparingInt(
								repoCommit -> repoCommit.getRevCommit()
										.getCommitTime())
								.reversed());
			}
