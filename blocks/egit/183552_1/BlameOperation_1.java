			info = computeRevisions(repository, currentHead, path,
					progress.newChild(2));
		}
		if (info == null) {
			return;
		}
		if (shell.isDisposed()) {
			return;
		}
		if (fileRevision != null) {
			storage = fileRevision.getStorage(progress.newChild(1));
		} else {
			progress.worked(1);
