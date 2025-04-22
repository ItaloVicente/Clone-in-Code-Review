		String refName = Constants.R_HEADS + Constants.MASTER;
		try {
			Ref ref = repository.getRef(Constants.HEAD);
			if (ref != null && ref.isSymbolic())
				refName = ref.getTarget().getName();
		} catch (IOException e) {
			Activator.logError("Error getting HEAD reference", e); //$NON-NLS-1$
		}
		showReflogFor(repository, refName);
	}

	private void showReflogFor(Repository repository, String ref) {
		if (repository != null && ref != null) {
			refLogTableTreeViewer.setInput(new ReflogInput(repository, ref));
			updateRefLink(ref);
