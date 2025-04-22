	private Repository getOrCloneSubmodule(SubmoduleWalk generator
			throws IOException
		Repository repository = generator.getRepository();
		if (repository == null) {
			if (callback != null) {
				callback.cloningSubmodule(generator.getPath());
			}
			CloneCommand clone = Git.cloneRepository();
			configure(clone);
			clone.setURI(url);
			clone.setDirectory(generator.getDirectory());
			clone.setGitDir(
					new File(new File(repo.getDirectory()
							generator.getPath()));
			if (monitor != null) {
				clone.setProgressMonitor(monitor);
			}
			repository = clone.call().getRepository();
		} else if (this.fetch) {
			if (fetchCallback != null) {
				fetchCallback.fetchingSubmodule(generator.getPath());
			}
			FetchCommand fetchCommand = Git.wrap(repository).fetch();
			if (monitor != null) {
				fetchCommand.setProgressMonitor(monitor);
			}
			configure(fetchCommand);
			fetchCommand.call();
		}
		return repository;
	}

