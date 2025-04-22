	public CommitBuilder amendRef(String ref) throws Exception {
		String name = normalizeRef(ref);
		Ref r = db.getRef(name);
		if (r == null)
			throw new IOException("Not a ref: " + ref);
		return amend(pool.parseCommit(r.getObjectId())
	}

	public CommitBuilder amend(AnyObjectId id) throws Exception {
		return amend(pool.parseCommit(id)
	}

	private CommitBuilder amend(RevCommit old
		b.author(old.getAuthorIdent());
		b.committer(old.getCommitterIdent());
		b.updateCommitterTime = true;

		b.noParents();
		for (int i = 0; i < old.getParentCount(); i++)
			b.parent(old.getParent(i));

		b.tree.clear();
		try (TreeWalk tw = new TreeWalk(db)) {
			tw.reset(old.getTree());
			tw.setRecursive(true);
			while (tw.next()) {
				b.edit(new PathEdit(tw.getPathString()) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setFileMode(tw.getFileMode(0));
						ent.setObjectId(tw.getObjectId(0));
					}
				});
			}
		}

		return b;
	}

