		private void createRepositorySwitchAction() {
			switchRepositoryAction = new RepositoryToolbarAction(true, repo -> {
				Repository current = historyPage.currentRepo;
				if (current != null
						&& repo.getDirectory().equals(current.getDirectory())) {
					HistoryPageInput currentInput = historyPage
							.getInputInternal();
					if (currentInput != null && currentInput.getItems() == null
							&& currentInput.getFileList() == null) {
						return;
					}
				}
				if (historyPage.selectionTracker != null) {
					historyPage.selectionTracker.clearSelection();
				}
				historyPage.getHistoryView()
						.showHistoryFor(new RepositoryNode(null, repo));
			});
			actionsToDispose.add(switchRepositoryAction);
		}

