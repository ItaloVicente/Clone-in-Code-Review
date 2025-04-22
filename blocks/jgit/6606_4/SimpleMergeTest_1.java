
	private ObjectId commit(final ObjectInserter odi
			final AnyObjectId parentId1
			throws Exception {
		final CommitBuilder c = new CommitBuilder();
		c.setTreeId(treeB.writeTree(odi));
		c.setAuthor(new PersonIdent("A U Thor"
		c.setCommitter(c.getAuthor());
		c.setParentIds(parentId1
		c.setMessage("Tree " + c.getTreeId().name());
		ObjectId id = odi.insert(c);
		odi.flush();
		return id;
	}

	private ObjectId commit(final ObjectInserter odi
			List<ObjectId> parents) throws Exception {
		final CommitBuilder c = new CommitBuilder();
		c.setTreeId(treeB.writeTree(odi));
		c.setAuthor(new PersonIdent("A U Thor"
		c.setCommitter(c.getAuthor());
		c.setParentIds(parents);
		c.setMessage("Tree " + c.getTreeId().name());
		ObjectId id = odi.insert(c);
		odi.flush();
		return id;
	}
