
	private IProject[] getMissingProjects(String branch,
			IProject[] currentProjects) {
		if (delete || currentProjects.length == 0)
			return new IProject[0];

		ObjectId targetTreeId;
		ObjectId currentTreeId;
		try {
			targetTreeId = repository.resolve(branch + "^{tree}"); //$NON-NLS-1$
			currentTreeId = repository.resolve(Constants.HEAD + "^{tree}"); //$NON-NLS-1$
		} catch (IOException e) {
			return new IProject[0];
		}
		if (targetTreeId == null || currentTreeId == null)
			return new IProject[0];

		Map<String, IProject> locations = new HashMap<String, IProject>();
		for (IProject project : currentProjects) {
			IPath location = project.getLocation();
			if (location == null)
				continue;
			locations.put(
					location.append(IProjectDescription.DESCRIPTION_FILE_NAME)
							.toFile().getAbsolutePath(), project);
		}

		List<IProject> toBeClosed = new ArrayList<IProject>();
		File root = repository.getWorkTree();
		TreeWalk walk = new TreeWalk(repository);
		try {
			walk.addTree(targetTreeId);
			walk.addTree(currentTreeId);
			walk.addTree(new FileTreeIterator(repository));
			walk.setRecursive(true);
			walk.setFilter(AndTreeFilter.create(PathSuffixFilter
					.create(IProjectDescription.DESCRIPTION_FILE_NAME),
					TreeFilter.ANY_DIFF));
			while (walk.next()) {
				AbstractTreeIterator targetIter = walk.getTree(0,
						AbstractTreeIterator.class);
				if (targetIter != null)
					continue;

				AbstractTreeIterator currentIter = walk.getTree(1,
						AbstractTreeIterator.class);
				AbstractTreeIterator workingIter = walk.getTree(2,
						AbstractTreeIterator.class);
				if (currentIter == null || workingIter == null)
					continue;

				String path = new File(root, walk.getPathString())
						.getAbsolutePath();
				IProject project = locations.get(path);
				if (project != null)
					toBeClosed.add(project);
			}
		} catch (IOException e) {
			return new IProject[0];
		} finally {
			walk.release();
		}
		return toBeClosed.toArray(new IProject[toBeClosed.size()]);
	}
