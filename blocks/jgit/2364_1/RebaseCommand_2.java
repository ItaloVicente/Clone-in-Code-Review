	public RevCommit tryFastForward(RevCommit newCommit)
			throws RefNotFoundException
		Ref head = repo.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved

		ObjectId headId = head.getObjectId();
		if (headId == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		RevCommit headCommit = walk.lookupCommit(headId);
		if (walk.isMergedInto(newCommit
			return newCommit;

		String headName;
		if (head.isSymbolic())
			headName = head.getTarget().getName();
		else
			headName = "detached HEAD";
		return tryFastForward(headName
	}

	private RevCommit tryFastForward(String headName
			RevCommit newCommit) throws IOException
		boolean tryRebase = false;
		for (RevCommit parentCommit : newCommit.getParents())
			if (parentCommit.equals(oldCommit))
				tryRebase = true;
		if (!tryRebase)
			return null;

		CheckoutCommand co = new CheckoutCommand(repo);
		try {
			co.setName(newCommit.name()).call();
			if (headName.startsWith(Constants.R_HEADS)) {
				RefUpdate rup = repo.updateRef(headName);
				rup.setExpectedOldObjectId(oldCommit);
				rup.setNewObjectId(newCommit);
				rup.setRefLogMessage("Fast-foward from " + oldCommit.name()
						+ " to " + newCommit.name()
				Result res = rup.update(walk);
				switch (res) {
				case FAST_FORWARD:
				case NO_CHANGE:
				case FORCED:
					break;
				default:
					throw new IOException("Could not fast-forward");
				}
			}
			return newCommit;
		} catch (RefAlreadyExistsException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (RefNotFoundException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (InvalidRefNameException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

