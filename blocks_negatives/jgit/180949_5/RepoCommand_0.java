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
						throw new ConcurrentRefUpdateException(
								MessageFormat.format(
										JGitText.get().cannotLock, targetBranch),
								ru.getRef(),
								rc);
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().updatingRefFailed,
								targetBranch, commitId.name(), rc));
