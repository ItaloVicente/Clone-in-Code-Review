			newCommit = c;
			newObjectId = d.getBlobs()[1];
		}

		IWorkbenchPage page = site.getWorkbenchWindow().getActivePage();
		if (oldCommit != null && newCommit != null) {
			IFile file = ResourceUtil.getFileForLocation(getRepository(), np, false);
			try {
				if (file != null) {
					IResource[] resources = new IResource[] { file, };
					CompareUtils.compare(resources, getRepository(), np, op,
							newCommit.getName(), oldCommit.getName(), false,
							page);
				} else {
					IPath location = new Path(getRepository().getWorkTree()
							.getAbsolutePath()).append(np);
					CompareUtils.compare(location, getRepository(),
							newCommit.getName(), oldCommit.getName(), false,
							page);
				}
			} catch (Exception e) {
				Activator.logError(UIText.GitHistoryPage_openFailed, e);
				Activator.showError(UIText.GitHistoryPage_openFailed, null);
			}
			return;
