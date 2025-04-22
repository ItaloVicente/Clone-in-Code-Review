	private static GitCommand<?> prepareCommand(Repository repository,
			Collection<String> paths) {
		Git git = new Git(repository);
		if (hasHead(repository)) {
			ResetCommand resetCommand = git.reset();
			resetCommand.setRef(HEAD);
			for (String path : paths)
				resetCommand.addPath(getCommandPath(path));
			return resetCommand;
		} else {
			RmCommand rmCommand = git.rm();
			rmCommand.setCached(true);
			for (String path : paths)
				rmCommand.addFilepattern(getCommandPath(path));
			return rmCommand;
		}
	}

	private static boolean hasHead(Repository repository) {
		try {
			Ref head = repository.getRef(HEAD);
			return head != null && head.getObjectId() != null;
		} catch (IOException e) {
			return false;
		}
	}

	private static String getCommandPath(String path) {
		if ("".equals(path)) // Working directory //$NON-NLS-1$
			return "."; //$NON-NLS-1$
		else
			return path;
	}
