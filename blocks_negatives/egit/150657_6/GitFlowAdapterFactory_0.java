			} else if (adaptableObject instanceof IHistoryView) {
				IHistoryView historyView = (IHistoryView) adaptableObject;
				IHistoryPage historyPage = historyView.getHistoryPage();
				Object input = historyPage.getInput();
				if (input instanceof RepositoryNode) {
					RepositoryNode node = (RepositoryNode) input;
					repository = node.getRepository();
				} else if (input instanceof IResource) {
					repository = getRepository((IResource) input);
				}
