	private void addUntracked() throws CoreException {
		if (notTracked == null || notTracked.size() == 0)
			return;
		AddCommand addCommand = new Git(repo).add();
		boolean fileAdded = false;
		for (String path:notTracked)
			if (commitFileList.contains(path)) {
				addCommand.addFilepattern(path);
				fileAdded = true;
			}
		if (fileAdded) {
			try {
				addCommand.call();
			} catch (NoFilepatternException e) {
				throw new CoreException(Activator.error(e.getMessage(), e));
			}
