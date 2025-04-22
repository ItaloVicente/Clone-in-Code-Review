
			for (IProject proj : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if (proj.getLocation().equals(
						new Path(prj.getObject().getAbsolutePath())))
					importableProjectsOnly = false;

			}

		}

		boolean singleRef = refs.size() == 1 && projects.isEmpty()
				&& repos.isEmpty();
		boolean singleRepo = repos.size() == 1 && projects.isEmpty()
				&& refs.isEmpty();

		try {
			singleRef = singleRef
					&& !refs.get(0).getObject().getName()
							.equals(Constants.HEAD)
					&& (refs.get(0).getRepository().mapCommit(
							refs.get(0).getObject().getLeaf().getObjectId()) != null);
		} catch (IOException e2) {
			singleRef = false;
