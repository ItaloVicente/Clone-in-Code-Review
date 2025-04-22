		} else if (indexDiff.getUntracked().contains(path)) {
			prefix = UIText.CommitDialog_StatusUntracked;
		} else if (indexDiff.getRemoved().contains(path)) {
			prefix = UIText.CommitDialog_StatusRemoved;
		} else if (indexDiff.getMissing().contains(path)) {
			prefix = UIText.CommitDialog_StatusRemovedNotStaged;
		} else if (indexDiff.getModified().contains(path)) {
			prefix = UIText.CommitDialog_StatusModifiedNotStaged;
