		IProgressMonitor monitor;
		if (m == null)
			monitor = new NullProgressMonitor();
		else
			monitor = m;
		find(monitor, proj, searchLinkedFolders);
