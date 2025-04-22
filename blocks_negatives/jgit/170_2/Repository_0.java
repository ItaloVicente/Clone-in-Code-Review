			if (d != null) {
				String workTreeConfig = getConfig().getString("core", null, "worktree");
				if (workTreeConfig != null) {
					workDir = FS.resolve(d, workTreeConfig);
				} else {
					workDir = gitDir.getParentFile();
				}
