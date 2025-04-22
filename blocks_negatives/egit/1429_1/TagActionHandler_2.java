	private RevWalk getRevCommits(ExecutionEvent event)
			throws ExecutionException {
		RevWalk revWalk = new RevWalk(repo);
		revWalk.sort(RevSort.COMMIT_TIME_DESC, true);
		revWalk.sort(RevSort.BOUNDARY, true);

		try {
			AnyObjectId headId = repo.resolve(Constants.HEAD);
			if (headId != null)
				revWalk.markStart(revWalk.parseCommit(headId));
		} catch (IOException e) {
			ErrorDialog.openError(getShell(event),
					UIText.TagAction_errorDuringTagging,
					UIText.TagAction_errorWhileGettingRevCommits, new Status(
							IStatus.ERROR, Activator.getPluginId(), e
									.getMessage(), e));
		}

		return revWalk;
	}

