	private void collectProjectDirectories(HashSet<File> res, File root, IProgressMonitor monitor) {
		if (new File(root, IProjectDescription.DESCRIPTION_FILE_NAME).isFile()) {
			res.add(root);
		}
		if (!monitor.isCanceled()) {
			for (File child : root.listFiles()) {
				if (child.isDirectory()) {
					collectProjectDirectories(res, child, monitor);
				}
			}
		}
	}

