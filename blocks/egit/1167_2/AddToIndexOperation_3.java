		AddCommand command = addCommands.get(map);
		if (command == null) {
			Repository repo = map.getRepository();
			Git git = new Git(repo);
			AdaptableFileTreeIterator it =
				new AdaptableFileTreeIterator(repo.getWorkTree(),
						resource.getWorkspace().getRoot());
			command = git.add().setWorkingTreeIterator(it);
			addCommands.put(map, command);
