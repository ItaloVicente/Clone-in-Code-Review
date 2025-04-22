				List<RepositoryNode> selectedNodes;
				try {
					selectedNodes = getSelectedNodes(event);
				} catch (ExecutionException e) {
					Activator.logError(e.getMessage(), e);
					return new Status(IStatus.ERROR, Activator.getPluginId(), e
							.getMessage(), e);
				}
