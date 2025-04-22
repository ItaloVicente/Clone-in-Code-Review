			try (SubmoduleWalk smw = new SubmoduleWalk(repository)) {
				smw.setTree(new DirCacheIterator(dirCache));
				while (smw.next()) {
					try {
						if (localIgnoreSubmoduleMode == null)
							localIgnoreSubmoduleMode = smw.getModulesIgnore();
						if (IgnoreSubmoduleMode.ALL
								.equals(localIgnoreSubmoduleMode))
							continue;
					} catch (ConfigInvalidException e) {
						throw new IOException(MessageFormat.format(
								JGitText.get().invalidIgnoreParamSubmodule
								smw.getPath())
					}
					try (Repository subRepo = smw.getRepository()) {
						String subRepoPath = smw.getPath();
						if (subRepo != null) {
							if (subHead != null
									&& !subHead.equals(smw.getObjectId())) {
