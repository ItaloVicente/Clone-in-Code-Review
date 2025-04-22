			throw die(CLIText.get().cannotChekoutNoHeadsAdvertisedByRemote);
		if (!Constants.HEAD.equals(branch.getName())) {
			RefUpdate u = db.updateRef(Constants.HEAD);
			u.disableRefLog();
			u.link(branch.getName());
		}

		final RevCommit commit = parseCommit(branch);
		final RefUpdate u = db.updateRef(Constants.HEAD);
		u.setNewObjectId(commit);
		u.forceUpdate();

		DirCache dc = db.lockDirCache();
		DirCacheCheckout co = new DirCacheCheckout(db, dc, commit.getTree());
		co.checkout();
	}

	private RevCommit parseCommit(final Ref branch)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {
		final RevWalk rw = new RevWalk(db);
		final RevCommit commit;
