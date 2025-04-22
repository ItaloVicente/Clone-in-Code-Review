		Repository repository = getRepository(event);

		try {
			String dstRevCommit = commit.getId().getName();
			IWorkbenchPage workBenchPage = HandlerUtil
					.getActiveWorkbenchWindowChecked(event).getActivePage();
			if (input instanceof IFile) {
				IResource[] resources = new IResource[] { (IFile) input, };
				CompareUtils.compare(resources, repository, Constants.HEAD,
						dstRevCommit, true, workBenchPage);
			} else {
				IPath location = new Path(((File) input).getAbsolutePath());
				CompareUtils.compare(location, repository, Constants.HEAD,
						dstRevCommit, true, workBenchPage);
