		}
		return hasVisibleDescendants(folder, pattern);
	}

	private boolean hasVisibleDescendants(StagingFolderEntry folder,
			Pattern pattern) {
		for (Object child : folder.getChildren()) {
			if (child instanceof StagingFolderEntry) {
				if (hasVisibleDescendants((StagingFolderEntry) child,
						pattern)) {
					return true;
				}
			} else if (matches((StagingEntry) child, pattern)) {
				return true;
			}
		}
		return false;
