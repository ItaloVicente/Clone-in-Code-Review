		Job job = new Job("Refreshing Git Repositories view") { //$NON-NLS-1$

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				final List<RepositoryNode> oldInput = new ArrayList<RepositoryNode>();
				if (!needsNewInput) {
					Display.getDefault().syncExec(new Runnable() {

						public void run() {
							for (TreeItem item : getCommonViewer().getTree()
									.getItems()) {
								oldInput.add((RepositoryNode) item.getData());
							}
						}
					});
