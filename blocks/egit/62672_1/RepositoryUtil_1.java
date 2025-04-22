		String repoRelativePath = path
				.makeRelativeTo(
						new Path(repository.getWorkTree().getAbsolutePath()))
				.toString();
		if (repoRelativePath.length() == 0
				|| repoRelativePath.equals(path.toString())) {
