		copyAll = new Action(
				UIText.CommitFileDiffViewer_CopyAllFilePathsMenuLabel) {

			@Override
			public void run() {
				doCopy(Arrays
						.asList(((IStructuredContentProvider) getContentProvider())
								.getElements(getInput()))
						.iterator());
			}
		};
		mgr.add(copyAll);
