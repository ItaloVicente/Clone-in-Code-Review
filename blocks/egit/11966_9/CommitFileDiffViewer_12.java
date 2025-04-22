		IWorkbenchPage page = site.getWorkbenchWindow().getActivePage();
		if (leftCommit != null && rightCommit != null) {
			IFile file = ResourceUtil.getFileForLocation(getRepository(), np);
			try {
				if (file != null) {
					IResource[] resources = new IResource[] { file, };
					CompareUtils.compare(resources, getRepository(),
							leftCommit.getName(), rightCommit.getName(), false,
							page);
				} else {
					IPath location = new Path(getRepository().getWorkTree()
							.getAbsolutePath()).append(np);
					CompareUtils.compare(location, getRepository(),
							leftCommit.getName(), rightCommit.getName(), false,
							page);
