			int i = t.getTopIndex();
			if (i >= 0 && i < t.getItemCount()
					&& i < allCommitsLength
					&& allCommitsArray != null) {
				topCommitName = allCommitsArray[i].getId().name();
			}
