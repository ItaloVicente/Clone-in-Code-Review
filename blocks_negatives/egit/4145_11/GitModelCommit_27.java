	@Override
	protected GitModelObject[] getChildrenImpl() {
		TreeWalk tw = createTreeWalk();
		List<GitModelObject> result = new ArrayList<GitModelObject>();

		try {
			RevTree actualTree = baseCommit.getTree();

			int baseNth = tw.addTree(actualTree);
			int remoteNth = -1;
			if (remoteCommit != null)
				remoteNth = tw.addTree(remoteCommit.getTree());
			int ancestorNth = tw.addTree(ancestorCommit.getTree());

			while (tw.next()) {
				GitModelObject obj = getModelObject(tw, ancestorCommit, ancestorNth,
						remoteNth, baseNth);
				if (obj != null)
					result.add(obj);
