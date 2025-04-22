				Repository submoduleRepo = generator.getRepository();
				if (submoduleRepo == null) {
					if (callback != null) {
						callback.cloningSubmodule(generator.getPath());
					}
					CloneCommand clone = Git.cloneRepository();
					configure(clone);
					clone.setURI(url);
					clone.setDirectory(generator.getDirectory());
					clone.setGitDir(new File(new File(repo.getDirectory(),
							Constants.MODULES), generator.getPath()));
					if (monitor != null)
						clone.setProgressMonitor(monitor);
					submoduleRepo = clone.call().getRepository();
				} else if (this.fetch) {
					if (fetchCallback != null) {
						fetchCallback.fetchingSubmodule(generator.getPath());
					}
					FetchCommand fetchCommand = Git.wrap(submoduleRepo).fetch();
					if (monitor != null) {
						fetchCommand.setProgressMonitor(monitor);
					}
					configure(fetchCommand);
					fetchCommand.call();
				}

				try (RevWalk walk = new RevWalk(submoduleRepo)) {
