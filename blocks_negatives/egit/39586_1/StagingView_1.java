		try {
			Git git = new Git(currentRepository);
			ResetCommand reset = git.reset();
			for (String path : paths)
				reset.addPath(path);
			reset.call();
		} catch (GitAPIException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
