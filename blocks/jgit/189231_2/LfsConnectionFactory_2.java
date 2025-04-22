	private static Config getLfsConfig(Repository db)
			throws LfsConfigInvalidException {
		if (!db.isBare()) {
			File lfsConfig = db.getFS().resolve(db.getWorkTree()
					Constants.DOT_LFS_CONFIG);
			if (lfsConfig.exists() && lfsConfig.isFile()) {
				FileBasedConfig config = new FileBasedConfig(lfsConfig
						db.getFS());
				try {
					config.load();
					return config;
				} catch (ConfigInvalidException | IOException e) {
					throw new LfsConfigInvalidException(
							LfsText.get().dotLfsConfigReadFailed
				}
			}

			try {
				DirCacheEntry Entry = db.readDirCache()
						.getEntry(Constants.DOT_LFS_CONFIG);
				if (Entry != null) {
					return new BlobBasedConfig(null
				}
			} catch (NoWorkTreeException | IOException
					| ConfigInvalidException e) {
				throw new LfsConfigInvalidException(
						LfsText.get().dotLfsConfigReadFailed
			}
		}

		try (RevWalk revWalk = new RevWalk(db)) {
			ObjectId headCommitId = db
					.resolve(org.eclipse.jgit.lib.Constants.HEAD);
			RevCommit commit = revWalk.parseCommit(headCommitId);
			RevTree tree = commit.getTree();
			TreeWalk treewalk = TreeWalk.forPath(db
					tree);
			if (treewalk != null) {
				return new BlobBasedConfig(null
			}
		} catch (RevisionSyntaxException | IOException
				| ConfigInvalidException e) {
			throw new LfsConfigInvalidException(
					LfsText.get().dotLfsConfigReadFailed
		}

		return new Config();
	}

