	public CommitBuilder amend(String ref) throws Exception {
		Ref r = db.getRef(normalizeRef(ref));
		if (r == null)
			throw new IOException("Not a ref: " + ref);
		RevCommit old = pool.parseCommit(r.getObjectId());

		CommitBuilder b = branch(ref).commit();
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

