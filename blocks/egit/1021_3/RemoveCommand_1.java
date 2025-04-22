						RepositoriesView view;
						try {
							view = getView(event);
							view.getCommonViewer().refresh();
						} catch (ExecutionException e) {
							Activator.logError(e.getMessage(), e);
						}
