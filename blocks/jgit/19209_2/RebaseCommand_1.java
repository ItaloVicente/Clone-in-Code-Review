	private void autoStash() throws GitAPIException
		if (repo.getConfig().getBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
			String message = MessageFormat.format(
							AUTOSTASH_MSG
							Repository
									.shortenRefName(getHeadName(getHead())));
			RevCommit stashCommit = Git.wrap(repo).stashCreate().setRef(null)
					.setWorkingDirectoryMessage(
							message)
					.call();
			if (stashCommit != null) {
				FileUtils.mkdir(rebaseState.getDir());
				rebaseState.createFile(AUTOSTASH
			}
		}
	}

	private boolean autoStashApply() throws IOException
		boolean conflicts = false;
		if (rebaseState.getFile(AUTOSTASH).exists()) {
			String stash = rebaseState.readFile(AUTOSTASH);
			try {
				Git.wrap(repo).stashApply().setStashRef(stash)
						.ignoreRepositoryState(true).call();
			} catch (StashApplyFailureException e) {
				conflicts = true;
				RevWalk rw = new RevWalk(repo);
				ObjectId stashId = repo.resolve(stash);
				RevCommit commit = rw.parseCommit(stashId);
				updateStashRef(commit
						commit.getShortMessage());
			}
		}
		return conflicts;
	}

	private void updateStashRef(ObjectId commitId
			String refLogMessage) throws IOException {
		Ref currentRef = repo.getRef(Constants.R_STASH);
		RefUpdate refUpdate = repo.updateRef(Constants.R_STASH);
		refUpdate.setNewObjectId(commitId);
		refUpdate.setRefLogIdent(refLogIdent);
		refUpdate.setRefLogMessage(refLogMessage
		if (currentRef != null)
			refUpdate.setExpectedOldObjectId(currentRef.getObjectId());
		else
			refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
		refUpdate.forceUpdate();
	}

