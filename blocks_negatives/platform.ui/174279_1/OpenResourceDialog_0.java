	private boolean selectionContainsProjects() {
		for (Object item : getSelectedItems()) {
			if (item instanceof IProject) {
				return true;
			}
		}
		return false;
	}


