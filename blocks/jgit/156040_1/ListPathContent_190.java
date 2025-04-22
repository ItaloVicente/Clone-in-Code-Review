		final String gitPath = PathUtil.normalize(path);
		final List<PathInfo> result = new ArrayList<>();
		final ObjectId tree = git.getTreeFromRef(branchName);
		if (tree == null) {
			return result;
		}
		try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
			boolean found = false;
			if (gitPath.isEmpty()) {
				found = true;
			} else {
				tw.setFilter(PathFilter.create(gitPath));
			}
			tw.reset(tree);
			while (tw.next()) {
				if (!found && tw.isSubtree()) {
					tw.enterSubtree();
				}
				if (tw.getPathString().equals(gitPath)) {
					found = true;
					continue;
				}
				if (found) {
					result.add(new PathInfo(tw.getObjectId(0)
				}
			}
			return result;
		}
	}
