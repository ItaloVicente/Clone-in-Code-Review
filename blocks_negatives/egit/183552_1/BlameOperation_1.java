
		progress.worked(1);
		if (shell.isDisposed()) {
			return;
		}

		if (fileRevision != null) {
			storage = fileRevision.getStorage(progress.newChild(1));
		} else {
			progress.worked(1);
