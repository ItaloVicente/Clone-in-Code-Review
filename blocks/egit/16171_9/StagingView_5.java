								public void onRefsChanged(RefsChangedEvent event) {
									updateRebaseButtonVisibility(repository
											.getRepositoryState().isRebasing());
								}

							});
				}
