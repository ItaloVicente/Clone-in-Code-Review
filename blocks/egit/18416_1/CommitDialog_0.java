
				if (selection.size() == 1
						&& selection.getFirstElement() instanceof CommitItem) {
					CommitItem commitItem = (CommitItem) selection
							.getFirstElement();
					manager.add(createCompareAction(commitItem));
