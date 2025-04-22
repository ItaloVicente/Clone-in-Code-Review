						Repository repo = RepositoryCache.getInstance()
								.getRepository(change.getGitDirectory());
						if (repo == null || repo.isBare()) {
							progress.worked(1);
							continue;
						}
						WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
								change.getModified(), change.getDeleted());
						event.setRepository(repo);
						handler.refreshRepository(event,
								repo.getWorkTree().getAbsoluteFile(),
								progress.newChild(1));
