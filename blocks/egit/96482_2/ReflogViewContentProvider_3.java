		@Override
		public void fetchDeferredChildren(Object object,
				IElementCollector collector, IProgressMonitor monitor) {
			try (Git git = new Git(repository)) {
				refLog = git.reflog().setRef(ref).call();
				collector.add(refLog.toArray(), monitor);
