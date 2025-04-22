		try {
			InitCommand init = Git.init();
			init.setBare(bare).setDirectory(destination);
			init.call();
		} catch (JGitInternalException e) {
			throw new BuildException("Could not initialize repository"
		}
