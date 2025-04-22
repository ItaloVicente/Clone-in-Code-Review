	@Override
	public boolean isPageComplete() {
		if (viewer.getCheckedElements().length == 0)
			return false;
		for (Object checkedElement : viewer.getCheckedElements()) {
			String path = ((ProjectAndRepo)checkedElement).getRepo();
			if (((ProjectAndRepo)checkedElement).getRepo() != null &&
				return false;
