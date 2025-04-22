		showReflogFor(repository, Constants.R_HEADS + Constants.MASTER);
	}

	private void showReflogFor(Repository repository, String ref) {
		if (repository != null && ref != null) {
			refLogTableTreeViewer.setInput(new ReflogInput(repository, ref));
			updateRefLink(ref);
