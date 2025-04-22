		commit = walk.parseCommit(commit);
		if (newMessage.equals(commit.getFullMessage())) {
			return;
		}
		Ref ref = repository.exactRef(Constants.HEAD);
		if (ref == null) {
			throw new TeamException(CoreText.RewordCommitOperation_noHead);
		}
		headId = ref.getObjectId();
		if (headId == null || ObjectId.zeroId().equals(headId)) {
			throw new TeamException(CoreText.RewordCommitOperation_noHead);
		}
		String headName = ref.isSymbolic() ? ref.getLeaf().getName()
				: headId.name();
		Deque<RevCommit> commits = new LinkedList<>();
		walk.setRetainBody(true);
		if (!commit.getId().equals(headId)) {
			walk.sort(RevSort.TOPO);
			walk.sort(RevSort.COMMIT_TIME_DESC, true);
			walk.markStart(walk.parseCommit(headId));
			for (RevCommit p : commit.getParents()) {
				RevCommit parsed = walk.parseCommit(p);
				walk.markUninteresting(parsed);
			}
			RevCommit c;
			while ((c = walk.next()) != null) {
				if (c.getId().equals(commit.getId())) {
					break;
				}
				commits.push(c);
			}
			if (c == null) {
				throw new TeamException(MessageFormat.format(
						CoreText.RewordCommitOperation_notReachable,
						Utils.getShortObjectId(commit)));
			}
		}
		progress.worked(10);
		progress.setWorkRemaining(commits.size() + 2);
		PersonIdent committer = new PersonIdent(repository);
		CommitBuilder builder = copy(commit, commit.getParents(), committer,
				newMessage);
		GpgConfig gpgConfig = new GpgConfig(repository.getConfig());
		boolean signAllCommits = gpgConfig.isSignCommits();
		String keyId = gpgConfig.getSigningKey();
		GpgSigner gpgSigner = GpgSigner.getDefault();
		if (gpgSigner != null
				&& (signAllCommits || commit.getRawGpgSignature() != null)) {
			gpgSigner = sign(builder, gpgSigner, signAllCommits, keyId,
					committer, commit.getCommitterIdent(), commit);
		}
		Map<ObjectId, ObjectId> rewritten = new HashMap<>();
		String newCommitId = null;
		try (ObjectInserter inserter = repository.newObjectInserter()) {
			ObjectId newCommit = inserter.insert(builder);
			rewritten.put(commit.getId(), newCommit);
			newCommitId = newCommit.name();
			progress.worked(1);
			for (RevCommit c : commits) {
				RevCommit[] parents = c.getParents();
				ObjectId[] newParents = new ObjectId[parents.length];
				int i = 0;
				boolean hadNew = false;
				for (RevCommit p : parents) {
					ObjectId newId = rewritten.get(p.getId());
					if (newId != null) {
						hadNew = true;
