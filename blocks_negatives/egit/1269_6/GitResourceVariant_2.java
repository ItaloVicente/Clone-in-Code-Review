		TreeWalk tw = getTreeWalk(repo, revCommit.getTree(), path);
		if (tw == null) {
			objectId = null;
			this.variantPath = null;
		} else {
			objectId = tw.getObjectId(0);
			this.variantPath = new String(tw.getRawPath());
		}
