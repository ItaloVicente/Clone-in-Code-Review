	private List<String> calcTreeFilterPaths(Collection<String> filesToUpdate) {
		List<String> paths = new ArrayList<String>();
		for (String fileToUpdate : filesToUpdate) {
			for (String untrackedFolder : indexDiffData.getUntrackedFolders())
				if (fileToUpdate.startsWith(untrackedFolder))
					paths.add(untrackedFolder);
			paths.add(fileToUpdate);
		}
		return paths;
	}

