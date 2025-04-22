		IMenuManager repositoriesMenuManager = new MenuManager("Repositories"); //$NON-NLS-1$
		viewMenuMgr.add(repositoriesMenuManager);
		repositoriesMenuManager.setRemoveAllWhenShown(true);
		repositoriesMenuManager.addMenuListener(manager -> RepositoryMenuUtil
				.fillRepositories(manager, true, repo -> {
					if (currentRepo != null && repo.getDirectory()
							.equals(currentRepo.getDirectory())) {
						HistoryPageInput currentInput = getInputInternal();
						if (currentInput.getItems() == null
								&& currentInput.getFileList() == null) {
							return;
						}
					}
					if (selectionTracker != null) {
						selectionTracker.clearSelection();
					}
					GitHistoryPage.this.getHistoryView()
							.showHistoryFor(new RepositoryNode(null, repo));
				}));
