		if (workTree != null) {
			workDir = workTree;
			if (d == null)
				gitDir = new File(workTree, Constants.DOT_GIT);
			else
				gitDir = d;
		} else {
			if (d != null)
				gitDir = d;
			else
				throw new IllegalArgumentException(
						JGitText.get().eitherGIT_DIRorGIT_WORK_TREEmustBePassed);
		}

		this.fs = fs;

		userConfig = SystemReader.getInstance().openUserConfig(fs);
		repoConfig = new FileBasedConfig(userConfig, fs.resolve(gitDir, "config"));
