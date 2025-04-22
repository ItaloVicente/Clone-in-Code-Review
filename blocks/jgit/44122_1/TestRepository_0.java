	public RevCommit cherryPick(AnyObjectId id) throws Exception {
		RevCommit commit = pool.parseCommit(id);
		pool.parseBody(commit);
		if (commit.getParentCount() != 1)
			throw new IOException(String.format(
					"Expected 1 parent for %s
					id.name()
		RevCommit parent = commit.getParent(0);
		pool.parseHeaders(parent);

		Ref headRef = db.getRef(Constants.HEAD);
		if (headRef == null)
			throw new IOException("Missing HEAD");
		RevCommit head = pool.parseCommit(headRef.getObjectId());

		ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(db
		merger.setBase(parent.getTree());
		if (merger.merge(head
			if (AnyObjectId.equals(head.getTree()
				return null;
			tick(1);
			org.eclipse.jgit.lib.CommitBuilder b =
					new org.eclipse.jgit.lib.CommitBuilder();
			b.setParentId(head);
			b.setTreeId(merger.getResultTreeId());
			b.setAuthor(commit.getAuthorIdent());
			b.setCommitter(new PersonIdent(commit.getCommitterIdent()
			b.setMessage(commit.getFullMessage());
			ObjectId result;
			try (ObjectInserter ins = inserter) {
				result = ins.insert(b);
				ins.flush();
			}
			update(Constants.HEAD
			return pool.parseCommit(result);
		} else {
			throw new IOException("Merge conflict");
		}
	}

