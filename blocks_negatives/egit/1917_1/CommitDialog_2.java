				if (indexEntry == null) {
					prefix = UIText.CommitDialog_StatusUntracked;
				}
				else if (indexEntry.isModified(repositoryMapping.getWorkTree()))
					prefix = UIText.CommitDialog_StatusAddedIndexDiff;
			} else if (indexEntry == null) {
				prefix = UIText.CommitDialog_StatusRemoved;
			} else if (headExists
					&& !headEntry.getId().equals(indexEntry.getObjectId())) {
