		String path = stripWorkDir(repo.getWorkTree(), res.getLocation()
				.toFile());

		TreeWalk tw = new TreeWalk(repo);
		if (path.length() > 0)
			tw.setFilter(PathFilter.create(path));
		tw.setRecursive(true);

		Set<IResource> gitMembers = new HashSet<IResource>();
		Map<String, IResource> allMembers = new HashMap<String, IResource>();
		try {
			tw.addTree(new FileTreeIterator(repo));
			for (IResource member : ((IContainer) res).members())
				allMembers.put(member.getName(), member);

			while (tw.next()) {
				if (tw.getTree(0, FileTreeIterator.class).isEntryIgnored())
					continue;

				IResource member = allMembers.get(tw.getNameString());
				if (member != null)
					gitMembers.add(member);
