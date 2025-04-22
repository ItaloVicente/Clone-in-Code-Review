		IResource[] selected = getSelectedResources();
		if (selected.length != 1 || !(selected[0] instanceof IStorage))
			return null;

		Repository repository = getRepository();
		if (repository == null)
			return null;
