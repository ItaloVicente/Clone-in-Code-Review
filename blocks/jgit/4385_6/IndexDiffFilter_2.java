	private void copyUntrackedFolders(String currentPath) {
		String pathToBeSaved = null;
		while (!untrackedParentFolders.isEmpty()
				&& !currentPath.startsWith(untrackedParentFolders.getFirst()
						+ "/"))
			pathToBeSaved = untrackedParentFolders.removeFirst();
		if (pathToBeSaved != null) {
			while (!untrackedFolders.isEmpty()
					&& untrackedFolders.getLast().startsWith(pathToBeSaved))
				untrackedFolders.removeLast();
			untrackedFolders.addLast(pathToBeSaved);
		}
	}

