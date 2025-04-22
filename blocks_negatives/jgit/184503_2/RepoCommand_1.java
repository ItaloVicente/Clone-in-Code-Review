	private RevCommit commitTreeOnCurrentTip(ObjectInserter inserter,
			RevWalk rw, ObjectId treeId)
			throws IOException, ConcurrentRefUpdateException {
		if (headId != null && rw.parseCommit(headId).getTree().getId().equals(treeId)) {
			return rw.parseCommit(headId);
		}

		CommitBuilder commit = new CommitBuilder();
		commit.setTreeId(treeId);
		if (headId != null)
			commit.setParentIds(headId);
		commit.setAuthor(author);
		commit.setCommitter(author);
		commit.setMessage(RepoText.get().repoCommitMessage);

		ObjectId commitId = inserter.insert(commit);
		inserter.flush();

		RefUpdate ru = repo.updateRef(targetBranch);
		ru.setNewObjectId(commitId);
		ru.setExpectedOldObjectId(headId != null ? headId : ObjectId.zeroId());
		Result rc = ru.update(rw);
		switch (rc) {
			case NEW:
			case FORCED:
			case FAST_FORWARD:
				break;
			case REJECTED:
			case LOCK_FAILURE:
				throw new ConcurrentRefUpdateException(MessageFormat
						.format(JGitText.get().cannotLock, targetBranch),
						ru.getRef(), rc);
			default:
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().updatingRefFailed,
						targetBranch, commitId.name(), rc));
		}

		return rw.parseCommit(commitId);
	}

	private void addSubmodule(String name, String url, String path,
			String revision, List<CopyFile> copyfiles, List<LinkFile> linkfiles,
			Git git) throws GitAPIException, IOException {
		assert (!repo.isBare());
		assert (git != null);
		if (!linkfiles.isEmpty()) {
			throw new UnsupportedOperationException(
					JGitText.get().nonBareLinkFilesNotSupported);
		}

		SubmoduleAddCommand add = git.submoduleAdd().setName(name).setPath(path)
				.setURI(url);
		if (monitor != null)
			add.setProgressMonitor(monitor);

		Repository subRepo = add.call();
		if (revision != null) {
			try (Git sub = new Git(subRepo)) {
				sub.checkout().setName(findRef(revision, subRepo)).call();
			}
			subRepo.close();
			git.add().addFilepattern(path).call();
		}
		for (CopyFile copyfile : copyfiles) {
			copyfile.copy();
			git.add().addFilepattern(copyfile.dest).call();
		}
