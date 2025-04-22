		CommonViewer viewer = super.createCommonViewer(aParent);

		viewer.addOpenListener(new IOpenListener() {

			public void open(OpenEvent event) {
				TreeSelection sel = (TreeSelection) event.getSelection();
				RepositoryTreeNode element = (RepositoryTreeNode) sel
						.getFirstElement();

				if (element instanceof RefNode || element instanceof FileNode
						|| element instanceof TagNode) {
					IHandlerService srv = (IHandlerService) getViewSite()
							.getService(IHandlerService.class);
					ICommandService csrv = (ICommandService) getViewSite()
							.getService(ICommandService.class);
					Command openCommand = csrv
							.getCommand("org.eclipse.egit.ui.RepositoriesViewOpen"); //$NON-NLS-1$
					ExecutionEvent evt = srv.createExecutionEvent(openCommand,
							null);

					try {
						openCommand.executeWithChecks(evt);
					} catch (Exception e) {
						e.printStackTrace();
					}
