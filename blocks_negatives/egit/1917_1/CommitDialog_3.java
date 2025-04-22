
				if (indexEntry.isModified(repositoryMapping.getWorkTree()))
					prefix = UIText.CommitDialog_StatusModifiedIndexDiff;
			} else if (!new File(repositoryMapping.getWorkTree(), indexEntry
					.getName()).isFile()) {
				prefix = UIText.CommitDialog_StatusRemovedNotStaged;
			} else if (indexEntry.isModified(repositoryMapping.getWorkTree())) {
				prefix = UIText.CommitDialog_StatusModifiedNotStaged;
			}

		} catch (Exception e) {
			Activator.logError(UIText.CommitDialog_problemFindingFileStatus, e);
			prefix = e.getMessage();
