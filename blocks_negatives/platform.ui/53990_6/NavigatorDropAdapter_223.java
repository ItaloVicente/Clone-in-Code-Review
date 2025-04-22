        Display.getCurrent().asyncExec(new Runnable() {
            @Override
			public void run() {
                getShell().forceActive();
				new CopyFilesAndFoldersOperation(getShell()).copyOrLinkFiles(names, target, currentOperation);
            }
        });
