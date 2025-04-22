				WorkingTreeChanges changes = repositoriesChanged.get(gitDir);
				if (changes == null) {
					repositoriesChanged.put(gitDir,
							new WorkingTreeChanges(event));
				} else {
					changes.merge(event);
					if (changes.isEmpty()) {
						repositoriesChanged.remove(gitDir);
					}
				}
