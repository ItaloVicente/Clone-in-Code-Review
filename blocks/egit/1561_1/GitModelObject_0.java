	protected List<String> getNotIgnoredNodes(ObjectId root) throws IOException {
		Repository repo = getRepository();
		List<String> resutl = new ArrayList<String>();

		TreeWalk tw = new TreeWalk(repo);
		tw.reset();
		tw.addTree(root);

		int ignoreNth = tw.addTree(new FileTreeIterator(repo));
		tw.setFilter(new NotIgnoredFilter(ignoreNth));

		while (tw.next())
			resutl.add(tw.getNameString());
		return resutl;
	}

