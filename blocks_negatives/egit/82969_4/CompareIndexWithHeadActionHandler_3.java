
	private boolean isStaged(Repository repository, IPath location,
			boolean checkIndex) {
		if (location == null || location.toFile().isDirectory()
				|| repository.isBare()) {
			return false;
		}
		IPath workDir = new Path(repository.getWorkTree().getAbsolutePath());
		String resRelPath = location.makeRelativeTo(workDir).toString();
		if (resRelPath.length() == 0
				|| resRelPath.equals(location.toString())) {
			return false;
		}

		if (!checkIndex) {
			return true;
		}
		try {
			FileTreeIterator fileTreeIterator = new FileTreeIterator(repository);
			IndexDiff indexDiff = new IndexDiff(repository, Constants.HEAD,
					fileTreeIterator);
			indexDiff.setFilter(PathFilterGroup.createFromStrings(Collections.singletonList(resRelPath)));
			indexDiff.diff();

			return indexDiff.getAdded().contains(resRelPath) || indexDiff.getChanged().contains(resRelPath)
					|| indexDiff.getRemoved().contains(resRelPath);
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					location.toString()), e);
			return false;
		}
