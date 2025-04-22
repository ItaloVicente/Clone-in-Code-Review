		tick(secDelta);
		final Commit c = new Commit(db);
		c.setTreeId(tree);
		c.setParentIds(parents);
		c.setAuthor(new PersonIdent(author, new Date(nowTick)));
		c.setCommitter(new PersonIdent(committer, new Date(nowTick)));
		c.setMessage("");
		return rw.lookupCommit(ow.writeCommit(c));
