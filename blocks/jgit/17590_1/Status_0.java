		StatusCommand statusCommand = new Git(db).status();
		if (filterPaths != null && filterPaths.size() > 0)
			for (String path : filterPaths)
				statusCommand.addPath(path);
		org.eclipse.jgit.api.Status status = statusCommand.call();
		printStatus(status);
	}

	private void printStatus(org.eclipse.jgit.api.Status status)
			throws IOException {
		if (porcelain)
			printPorcelainStatus(status);
		else
			printLongStatus(status);
	}

	private void printPorcelainStatus(org.eclipse.jgit.api.Status status)
			throws IOException {

		Collection<String> added = status.getAdded();
		Collection<String> changed = status.getChanged();
		Collection<String> removed = status.getRemoved();
		Collection<String> modified = status.getModified();
		Collection<String> missing = status.getMissing();
		Map<String

		TreeSet<String> sorted = new TreeSet<String>();
		sorted.addAll(added);
		sorted.addAll(changed);
		sorted.addAll(removed);
		sorted.addAll(modified);
		sorted.addAll(missing);
		sorted.addAll(conflicting.keySet());

		for (String path : sorted) {
			char x = ' ';
			char y = ' ';

			if (added.contains(path))
				x = 'A';
			else if (changed.contains(path))
				x = 'M';
			else if (removed.contains(path))
				x = 'D';

			if (modified.contains(path))
				y = 'M';
			else if (missing.contains(path))
				y = 'D';

			if (conflicting.containsKey(path)) {
				StageState stageState = conflicting.get(path);

				switch (stageState) {
				case BOTH_DELETED:
					x = 'D';
					y = 'D';
					break;
				case ADDED_BY_US:
					x = 'A';
					y = 'U';
					break;
				case DELETED_BY_THEM:
					x = 'U';
					y = 'D';
					break;
				case ADDED_BY_THEM:
					x = 'U';
					y = 'A';
					break;
				case DELETED_BY_US:
					x = 'D';
					y = 'U';
					break;
				case BOTH_ADDED:
					x = 'A';
					y = 'A';
					break;
				case BOTH_MODIFIED:
					x = 'U';
					y = 'U';
					break;
				default:
							+ stageState);
				}
			}

			printPorcelainLine(x
		}

		for (String path : status.getUntracked())
			printPorcelainLine('?'
	}

	private void printPorcelainLine(char x
			throws IOException {
		StringBuilder lineBuilder = new StringBuilder();
		lineBuilder.append(x).append(y).append(' ').append(path);
		outw.println(lineBuilder.toString());
	}

	private void printLongStatus(org.eclipse.jgit.api.Status status)
			throws IOException {
