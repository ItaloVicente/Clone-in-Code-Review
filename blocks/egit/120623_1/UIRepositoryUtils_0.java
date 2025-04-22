			return doDialog(repo, files, dialogTitle, shell);
		}
		return true;
	}

	private static boolean doDialog(Repository repo, List<String> files,
			String dialogTitle, Shell shell) {
		Collections.sort(files);
		CleanupUncomittedChangesDialog cleanupUncomittedChangesDialog = new CleanupUncomittedChangesDialog(
				shell, dialogTitle,
				UIText.AbstractRebaseCommandHandler_cleanupDialog_text, repo,
				files);
		cleanupUncomittedChangesDialog.open();
		return cleanupUncomittedChangesDialog.shouldContinue();
	}

	public static boolean handleUncommittedFiles(Repository repo,
			RevCommit commit, int parentIndex, String title, Shell shell)
			throws GitAPIException {
		int nofParents = commit.getParentCount();
		if (nofParents == 0) {
			return handleUncommittedFiles(repo, shell, title);
		}
		List<String> conflicts = new ArrayList<>();
		try (Git git = new Git(repo);
				DiffFormatter fmt = new DiffFormatter(
						new ByteArrayOutputStream())) {
			Status status = git.status().call();
			if (status != null && status.hasUncommittedChanges()) {
				Set<String> uncommitted = status.getUncommittedChanges();
				RevCommit parent = repo.parseCommit(commit.getParent(0));
				if (nofParents > 1 && parentIndex > 0
						&& parentIndex < nofParents) {
					parent = repo.parseCommit(commit.getParent(parentIndex));
				}
				fmt.setRepository(repo);
				for (DiffEntry diff : fmt.scan(commit, parent)) {
					switch (diff.getChangeType()) {
					case ADD:
					case COPY:
					case MODIFY:
						if (uncommitted.contains(diff.getNewPath())) {
							conflicts.add(diff.getNewPath());
						}
						break;
					case DELETE:
						if (uncommitted.contains(diff.getOldPath())) {
							conflicts.add(diff.getOldPath());
						}
						break;
					default:
						if (uncommitted.contains(diff.getNewPath())) {
							conflicts.add(diff.getNewPath());
						}
						if (uncommitted.contains(diff.getOldPath())) {
							conflicts.add(diff.getOldPath());
						}
						break;
					}
				}
			}
		} catch (IOException e) {
			return handleUncommittedFiles(repo, shell, title);
		}
		if (conflicts.isEmpty()) {
