
		if (fileRevision != null) {
			storage = fileRevision.getStorage(progress.newChild(1));
		} else {
			progress.worked(1);
		}
		shell.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				openEditor(info);
			}
		});
