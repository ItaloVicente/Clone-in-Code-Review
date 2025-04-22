					IPath subPath = ResourceUtil.getRepositoryRelativePath(path,
							repository);
					if (subPath == null || subPath.isEmpty()) {
						return null;
					}
					try (SubmoduleWalk walk = new SubmoduleWalk(repository)) {
						FileTreeIterator iterator = new FileTreeIterator(
								repository);
						walk.setTree(iterator);
						walk.setFilter(PathFilterGroup
								.createFromStrings(subPath.toString()));
						walk.setRootTree(iterator);
						if (walk.next()) {
							try (Repository sub = walk.getRepository()) {
								RevCommit headCommit = Activator.getDefault()
										.getRepositoryUtil()
										.parseHeadCommit(sub);
								if (headCommit == null) {
									return null;
								}
								return new ByteArrayInputStream(
										Constants.encode(headCommit.name()));
							}
