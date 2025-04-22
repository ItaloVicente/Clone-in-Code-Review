	private void checkIfEmpty(RevWalk rw
			throws EmptyCommitException
			IncorrectObjectTypeException
		if (headId != null && !allowEmpty.booleanValue()) {
			RevCommit headCommit = rw.parseCommit(headId);
			headCommit.getTree();
			if (indexTreeId.equals(headCommit.getTree())) {
				throw new EmptyCommitException(JGitText.get().emptyCommit);
			}
		}
	}

	private void sign(CommitBuilder commit) throws ServiceUnavailableException
			CanceledException
		if (gpgSigner == null) {
			throw new ServiceUnavailableException(
					JGitText.get().signingServiceUnavailable);
		}
		if (gpgSigner instanceof GpgObjectSigner) {
			((GpgObjectSigner) gpgSigner).signObject(commit
					signingKey
					gpgConfig);
		} else {
			if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
				throw new UnsupportedSigningFormatException(JGitText
						.get().onlyOpenPgpSupportedForSigning);
			}
			gpgSigner.sign(commit
					credentialsProvider);
		}
	}

	private void updateRef(RepositoryState state
			RevCommit revCommit
			throws ConcurrentRefUpdateException
		RefUpdate ru = repo.updateRef(Constants.HEAD);
		ru.setNewObjectId(commitId);
		if (!useDefaultReflogMessage) {
			ru.setRefLogMessage(reflogComment
		} else {
			ru.setRefLogMessage(prefix + revCommit.getShortMessage()
					false);
		}
		if (headId != null) {
			ru.setExpectedOldObjectId(headId);
		} else {
			ru.setExpectedOldObjectId(ObjectId.zeroId());
		}
		Result rc = ru.forceUpdate();
		switch (rc) {
		case NEW:
		case FORCED:
		case FAST_FORWARD: {
			setCallable(false);
			if (state == RepositoryState.MERGING_RESOLVED
					|| isMergeDuringRebase(state)) {
				repo.writeMergeCommitMsg(null);
				repo.writeMergeHeads(null);
			} else if (state == RepositoryState.CHERRY_PICKING_RESOLVED) {
				repo.writeMergeCommitMsg(null);
				repo.writeCherryPickHead(null);
			} else if (state == RepositoryState.REVERTING_RESOLVED) {
				repo.writeMergeCommitMsg(null);
				repo.writeRevertHead(null);
			}
			break;
		}
		case REJECTED:
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					JGitText.get().couldNotLockHEAD
		default:
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().updatingRefFailed
					commitId.toString()
		}
	}

