
				if (element instanceof RefNode || element instanceof FileNode
						|| element instanceof TagNode) {
					IHandlerService srv = (IHandlerService) getViewSite()
							.getService(IHandlerService.class);

					try {
						srv.executeCommand("org.eclipse.egit.ui.RepositoriesViewOpen", null); //$NON-NLS-1$
					} catch (Exception e) {
						Activator.handleError(e.getMessage(), e, false);
					}
