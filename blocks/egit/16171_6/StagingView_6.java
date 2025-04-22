					if (refsChangedListener != null)
						refsChangedListener.remove();
					refsChangedListener = repository.getListenerList()
							.addRefsChangedListener(new RefsChangedListener() {

								public void onRefsChanged(RefsChangedEvent event) {
									updateRebaseButtonVisibility(repository
											.getRepositoryState().isRebasing());
								}
