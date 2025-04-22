		c.setAuthor(ident);
		c.setCommitter(ident);
		ObjectInserter odi = db.newObjectInserter();
		ObjectId newCommitId = odi.insert(c);
		odi.flush();
		RevCommit ret = walk.lookupCommit(newCommitId);
		walk.parseHeaders(ret);
		return ret;
