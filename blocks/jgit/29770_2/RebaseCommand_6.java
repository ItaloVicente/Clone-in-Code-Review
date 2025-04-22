		return null;
	}

	private void writeMergeInfo(RevCommit commitToPick
			List<RevCommit> newParents) throws IOException {
		repo.writeMergeHeads(newParents.subList(1
		repo.writeMergeCommitMsg(commitToPick.getFullMessage());
	}

	private List<RevCommit> getNewParents(RevCommit commitToPick)
			throws IOException {
		List<RevCommit> newParents = new ArrayList<RevCommit>();
		for (int p = 0; p < commitToPick.getParentCount(); p++) {
			String parentHash = commitToPick.getParent(p).getName();
			if (!new File(rebaseState.getRewrittenDir()
				newParents.add(commitToPick.getParent(p));
			else {
				String newParent = RebaseState.readFile(
						rebaseState.getRewrittenDir()
				if (newParent.length() == 0)
					newParents.add(walk.parseCommit(repo
							.resolve(Constants.HEAD)));
				else
					newParents.add(walk.parseCommit(ObjectId
							.fromString(newParent)));
			}
		}
		return newParents;
	}

	private void writeCurrentCommit(RevCommit commit) throws IOException {
		RebaseState.appendToFile(rebaseState.getFile(CURRENT_COMMIT)
				commit.name());
	}

	private void writeRewrittenHashes() throws RevisionSyntaxException
			IOException {
		File currentCommitFile = rebaseState.getFile(CURRENT_COMMIT);
		if (!currentCommitFile.exists())
			return;

		String head = repo.resolve(Constants.HEAD).getName();
		String currentCommits = rebaseState.readFile(CURRENT_COMMIT);
			RebaseState
					.createFile(rebaseState.getRewrittenDir()
		FileUtils.delete(currentCommitFile);
