		try (Git git = new Git(db)) {
			db.getConfig().setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
			git.add().addFilepattern("symlink").call();
			git.commit().setMessage("commit").call();
		}
