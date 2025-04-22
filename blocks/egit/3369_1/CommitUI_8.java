		monitor.beginTask(UIText.CommitActionHandler_calculatingChanges, 1000);
		EclipseGitProgressTransformer jgitMonitor = new EclipseGitProgressTransformer(
				monitor);
		CountingVisitor counter = new CountingVisitor();
		for (IProject p : selectedProjects) {
			try {
				p.accept(counter);
			} catch (CoreException e) {
