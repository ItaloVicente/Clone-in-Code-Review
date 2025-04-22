
	private ObjectId commit(final ObjectInserter odi, final DirCache treeB,
			final AnyObjectId parentId1, final AnyObjectId parentId2)
			throws Exception {
		final CommitBuilder c = new CommitBuilder();
		c.setTreeId(treeB.writeTree(odi));
		c.setAuthor(new PersonIdent("A U Thor", "a.u.thor", 1L, 0));
		c.setCommitter(c.getAuthor());
		c.setParentIds(parentId1, parentId2);
		c.setMessage("Tree " + c.getTreeId().name());
		ObjectId id = odi.insert(c);
		odi.flush();
		return id;
	}

	private ObjectId commit(final ObjectInserter odi, final DirCache treeB,
			List<ObjectId> parents) throws Exception {
		final CommitBuilder c = new CommitBuilder();
		c.setTreeId(treeB.writeTree(odi));
		c.setAuthor(new PersonIdent("A U Thor", "a.u.thor", 1L, 0));
		c.setCommitter(c.getAuthor());
		c.setParentIds(parents);
		c.setMessage("Tree " + c.getTreeId().name());
		ObjectId id = odi.insert(c);
		odi.flush();
		return id;
	}
