		}
		try (Git git = new Git(repo)) {
			AddCommand addCommand = git.add();
			boolean fileAdded = false;
			for (String path : notTracked)
				if (commitFileList.contains(path)) {
					addCommand.addFilepattern(path);
					fileAdded = true;
				}
			if (fileAdded) {
