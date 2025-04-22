		try (Git git = new Git(repository)) {
			if (hasHead(repository)) {
				ResetCommand resetCommand = git.reset();
				resetCommand.setRef(HEAD);
				for (String path : paths) {
					resetCommand.addPath(getCommandPath(path));
				}
				return resetCommand;
			} else {
				RmCommand rmCommand = git.rm();
				rmCommand.setCached(true);
				for (String path : paths) {
					rmCommand.addFilepattern(getCommandPath(path));
				}
				return rmCommand;
			}
