				StagingEntryComparator comparator = (StagingEntryComparator) unstagedViewer
						.getComparator();
				comparator.setAlphabeticSort(!isChecked());
				comparator = (StagingEntryComparator) stagedViewer.getComparator();
				comparator.setAlphabeticSort(!isChecked());
				unstagedViewer.refresh();
				stagedViewer.refresh();
