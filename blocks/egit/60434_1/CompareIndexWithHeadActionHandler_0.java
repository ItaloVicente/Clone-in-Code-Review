		if (mapping != null) {
			resRelPath = mapping.getRepoRelativePath(location);
		} else {
			IPath workDir = new Path(
					repository.getWorkTree().getAbsolutePath());
			resRelPath = location.makeRelativeTo(workDir).toString();
