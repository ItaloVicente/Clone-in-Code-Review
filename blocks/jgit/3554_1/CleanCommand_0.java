	public Set<String> dryRun() {
		Set<String> files = new TreeSet<String>();
		try {
			StatusCommand command = new StatusCommand(repo);
			Status status = command.call();
			for (String file : status.getUntracked()) {
				if (paths.isEmpty() || paths.contains(file)) {
					files.add(file);
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return files;
	}

