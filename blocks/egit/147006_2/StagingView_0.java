			return canDeleteAll(selection.toList());
		}

		private boolean canDeleteAll(List list) {
			for (Object element : list) {
				if (element instanceof StagingFolderEntry) {
					if (!canDeleteAll(Arrays.asList(
							((StagingFolderEntry) element).getChildren()))) {
						return false;
					}
				} else if (element instanceof StagingEntry) {
					StagingEntry entry = (StagingEntry) element;
					if (!entry.getAvailableActions()
							.contains(StagingEntry.Action.DELETE))
						return false;
				} else {
