		final Set<IFile> files = new HashSet<IFile>();
		List<IContainer> folders = new ArrayList<IContainer>();
		Set<IProject> projects = new HashSet<IProject>();

		for (IResource res : resources) {
			projects.add(res.getProject());
			if (Team.isIgnoredHint(res))
				continue;
			if (res.getType() == IResource.FILE)
				files.add((IFile) res);
			else
				folders.add((IContainer) res);
		}

		if (monitor.isCanceled())
			throw new InterruptedException();

