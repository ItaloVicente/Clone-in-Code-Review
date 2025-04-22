				Repository repo = event.getRepository();
				WorkingTreeModifiedEvent existing = repositoriesChanged
						.get(repo);
				if (existing == null) {
					existing = event;
				} else {
					mergeEvents(existing, event);
				}
				if (existing.isEmpty()) {
					repositoriesChanged.remove(repo);
				} else {
					repositoriesChanged.put(repo, existing);
				}
