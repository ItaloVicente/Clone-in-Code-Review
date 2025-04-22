		try (Git git = new Git(db)) {
			RmCommand command = git.rm();
			for (String p : paths)
				command.addFilepattern(p);
			command.call();
		}
