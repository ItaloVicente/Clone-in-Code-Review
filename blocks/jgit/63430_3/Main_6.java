			}
		}
	}

	void init(final TextBuiltin cmd) throws IOException {
		if (cmd.requiresRepository()) {
			cmd.init(openGitDir(gitdir)
		} else {
			cmd.init(null
