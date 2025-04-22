		final Date commitDate = new Date();
		final TimeZone timeZone = TimeZone.getDefault();

		final PersonIdent authorIdent = new PersonIdent(author);
		final PersonIdent committerIdent = new PersonIdent(committer);

		for (java.util.Map.Entry<Repository, Tree> entry : treeMap.entrySet()) {
			Tree tree = entry.getValue();
			Repository repo = tree.getRepository();
			writeTreeWithSubTrees(tree);

			ObjectId currentHeadId = repo.resolve(Constants.HEAD);
			ObjectId[] parentIds;
			if (amending) {
				parentIds = previousCommit.getParentIds();
			} else {
				if (currentHeadId != null)
					parentIds = new ObjectId[] { currentHeadId };
				else
					parentIds = new ObjectId[0];
			}
			Commit commit = new Commit(repo, parentIds);
			commit.setTree(tree);
			commit.setMessage(commitMessage);
			commit.setAuthor(new PersonIdent(authorIdent, commitDate, timeZone));
			commit.setCommitter(new PersonIdent(committerIdent, commitDate, timeZone));

			ObjectWriter writer = new ObjectWriter(repo);
			commit.setCommitId(writer.writeCommit(commit));

			final RefUpdate ru = repo.updateRef(Constants.HEAD);
			ru.setNewObjectId(commit.getCommitId());
			ru.setRefLogMessage(buildReflogMessage(commitMessage), false);
			if (ru.forceUpdate() == RefUpdate.Result.LOCK_FAILURE) {
				throw new TeamException(
						NLS.bind(UIText.CommitAction_failedToUpdate, ru.getName(),
						commit.getCommitId()));
			}
		}
	}

	private void prepareTrees(IFile[] selectedItems,
			HashMap<Repository, Tree> treeMap) throws IOException,
			UnsupportedEncodingException {
		if (selectedItems.length == 0) {
			for (IProject proj : getProjectsForSelectedResources()) {
				Repository repo = RepositoryMapping.getMapping(proj).getRepository();
				if (!treeMap.containsKey(repo))
					treeMap.put(repo, repo.mapTree(Constants.HEAD));
			}
		}

		for (IFile file : selectedItems) {

			IProject project = file.getProject();
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(project);
			Repository repository = repositoryMapping.getRepository();
			Tree projTree = treeMap.get(repository);
			if (projTree == null) {
				projTree = repository.mapTree(Constants.HEAD);
				if (projTree == null)
					projTree = new Tree(repository);
				treeMap.put(repository, projTree);
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(),
			}
			GitIndex index = repository.getIndex();
			String repoRelativePath = repositoryMapping
					.getRepoRelativePath(file);
			String string = repoRelativePath;

			TreeEntry treeMember = projTree.findBlobMember(repoRelativePath);
			if (treeMember != null)
				treeMember.delete();

			Entry idxEntry = index.getEntry(string);
			if (notIndexed.contains(file)) {
				File thisfile = new File(repositoryMapping.getWorkDir(), idxEntry.getName());
				if (!thisfile.isFile()) {
					index.remove(repositoryMapping.getWorkDir(), thisfile);
					index.write();
					if (GitTraceLocation.UI.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.UI.getLocation(),
								"Phantom file, so removing from index"); //$NON-NLS-1$
					continue;
				} else {
					if (idxEntry.update(thisfile))
						index.write();
