			Repository repo = map.getRepository();
			Git git = new Git(repo);
			AdaptableFileTreeIterator it = new AdaptableFileTreeIterator(repo,
					resource.getWorkspace().getRoot());
			command = git.add().setWorkingTreeIterator(it);
			addCommands.put(map, command);
