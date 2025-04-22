	private static void closeMissingProject(IProject p, File projectFile,
			IProgressMonitor monitor) throws CoreException {
		boolean closeFailed = false;
		File projectRoot = projectFile.getParentFile();
		if (!projectRoot.isFile()) {
			boolean hasRoot = projectRoot.exists();
			try {
				if (!hasRoot)
					FileUtils.mkdirs(projectRoot, true);
				if (projectFile.createNewFile())
					p.close(new SubProgressMonitor(monitor, 1));
				else
					closeFailed = true;
			} catch (IOException e) {
				closeFailed = true;
			} finally {
				try {
					FileUtils.delete(projectFile, FileUtils.RETRY
							| FileUtils.SKIP_MISSING);
				} catch (IOException e) {
					closeFailed = true;
				}
				if (hasRoot)
					try {
						FileUtils.delete(projectRoot, FileUtils.RETRY
								| FileUtils.SKIP_MISSING | FileUtils.RECURSIVE);
					} catch (IOException e) {
						closeFailed = true;
					}
			}
		} else
			closeFailed = true;
		if (closeFailed)
			p.delete(false, true, new SubProgressMonitor(monitor, 1));
	}

