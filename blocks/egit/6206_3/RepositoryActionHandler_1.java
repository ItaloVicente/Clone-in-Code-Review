				if (directParents.contains(previousCommit)) {
					String previousPath = getPreviousPath(repository,
							rw.getObjectReader(), headCommit, previousCommit,
							path);
					result.add(new PreviousCommit(previousCommit, previousPath));
				}
