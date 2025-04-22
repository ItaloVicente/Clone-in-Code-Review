				RefsChangedListener listener = new RefsChangedListener() {
					@Override
					public void onRefsChanged(RefsChangedEvent event) {
						synchronized (RepositoriesViewContentProvider.this) {
							branchRefs.remove(repo);
						}
					}
				};
