	private boolean anyElementExistsInWorkspace(IStructuredSelection s) {
		for (Object element : s.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				if (entry.getFile() != null && entry.getFile().exists())
					return true;
			}
		}
		return false;
	}

