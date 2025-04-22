	protected void validateCommands() {
		for (ReceiveCommand cmd : commands) {
			final Ref ref = cmd.getRef();
			if (cmd.getResult() != Result.NOT_ATTEMPTED)
				continue;

			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				if (!isAllowDeletes()) {
					cmd.setResult(Result.REJECTED_NODELETE);
					continue;
				}
				if (!isAllowBranchDeletes()
						&& ref.getName().startsWith(Constants.R_HEADS)) {
					cmd.setResult(Result.REJECTED_NODELETE);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.CREATE) {
				if (!isAllowCreates()) {
					cmd.setResult(Result.REJECTED_NOCREATE);
					continue;
				}

				if (ref != null && !isAllowNonFastForwards()) {
					cmd.setResult(Result.REJECTED_NONFASTFORWARD);
					continue;
				}

				if (ref != null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().refAlreadyExists);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.DELETE && ref != null) {
				ObjectId id = ref.getObjectId();
				if (id == null) {
					id = ObjectId.zeroId();
				}
				if (!ObjectId.zeroId().equals(cmd.getOldId())
						&& !id.equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().invalidOldIdSent);
					continue;
				}
			}

			if (cmd.getType() == ReceiveCommand.Type.UPDATE) {
				if (ref == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().noSuchRef);
					continue;
				}
				ObjectId id = ref.getObjectId();
				if (id == null) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().cannotUpdateUnbornBranch);
					continue;
				}

				if (!id.equals(cmd.getOldId())) {
					cmd.setResult(Result.REJECTED_OTHER_REASON
							JGitText.get().invalidOldIdSent);
					continue;
				}

				RevObject oldObj
				try {
					oldObj = walk.parseAny(cmd.getOldId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT
							cmd.getOldId().name());
					continue;
				}

				try {
					newObj = walk.parseAny(cmd.getNewId());
				} catch (IOException e) {
					cmd.setResult(Result.REJECTED_MISSING_OBJECT
							cmd.getNewId().name());
					continue;
				}

				if (oldObj instanceof RevCommit
						&& newObj instanceof RevCommit) {
					try {
						if (walk.isMergedInto((RevCommit) oldObj
								(RevCommit) newObj))
							cmd.setTypeFastForwardUpdate();
						else
							cmd.setType(
									ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
					} catch (MissingObjectException e) {
						cmd.setResult(Result.REJECTED_MISSING_OBJECT
								e.getMessage());
					} catch (IOException e) {
						cmd.setResult(Result.REJECTED_OTHER_REASON);
					}
				} else {
					cmd.setType(ReceiveCommand.Type.UPDATE_NONFASTFORWARD);
				}

				if (cmd.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD
						&& !isAllowNonFastForwards()) {
					cmd.setResult(Result.REJECTED_NONFASTFORWARD);
					continue;
				}
			}

			if (!cmd.getRefName().startsWith(Constants.R_REFS)
					|| !Repository.isValidRefName(cmd.getRefName())) {
				cmd.setResult(Result.REJECTED_OTHER_REASON
						JGitText.get().funnyRefname);
			}
		}
	}

