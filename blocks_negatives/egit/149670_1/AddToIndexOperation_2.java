		AddCommand command = addCommands.get(map);
		if (command == null) {
			Repository repo = map.getRepository();
			try (Git git = new Git(repo)) {
				command = git.add();
			}
			addCommands.put(map, command);
		}
