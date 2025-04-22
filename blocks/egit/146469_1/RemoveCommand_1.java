				PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
					RepositoryCache repositoryCache = org.eclipse.egit.core.Activator
							.getDefault().getRepositoryCache();
					for (RepositoryNode node : selectedNodes) {
						repositoryCache.removeRepository(node.getRepository());
						node.clear();
