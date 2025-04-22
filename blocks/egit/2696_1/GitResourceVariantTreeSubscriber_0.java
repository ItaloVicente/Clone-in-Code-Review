	public boolean isSupervised(IResource res) throws TeamException {
		GitSynchronizeData gsd = gsds.getData(res.getProject());
		Repository repo = gsd.getRepository();

		boolean notIgnoredByGit = true;
		if (res.getLocation() != null) {
			String path = stripWorkDir(repo.getWorkTree(), res.getLocation()
					.toFile());

			TreeWalk tw = new TreeWalk(repo);
			if (path.length() > 0)
				tw.setFilter(PathFilter.create(path));
			tw.setRecursive(true);

			try {
				tw.addTree(new FileTreeIterator(repo));
				notIgnoredByGit = tw.next()
						&& !tw.getTree(0, FileTreeIterator.class)
								.isEntryIgnored();
			} catch (IOException e) {
				Activator.error(e.getMessage(), e);
			}
		}

		return gsds.contains(res.getProject()) && !isIgnoredHint(res)
				&& notIgnoredByGit;
