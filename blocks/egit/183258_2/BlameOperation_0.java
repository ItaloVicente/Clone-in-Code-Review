			IHistoryPage currentPage = part.getHistoryPage();
			if (currentPage instanceof GitHistoryPage
					&& input.baseEquals(currentPage.getInput())) {
				((GitHistoryPage) currentPage).refresh(revision.getCommit());
			} else {
				part.showHistoryFor(input);
			}
