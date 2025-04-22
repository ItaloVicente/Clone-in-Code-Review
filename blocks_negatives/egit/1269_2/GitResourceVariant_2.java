		this.revCommit = revCommit;
		TreeWalk tw = getTreeWalk(repo, revCommit.getTree(), path);
		if (tw == null)
			objectId = null;
		else
			objectId = tw.getObjectId(0);
