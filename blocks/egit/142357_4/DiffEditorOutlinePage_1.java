			if (selected.size() == 1 && !haveNew.isEmpty()
					&& !haveOld.isEmpty()) {
				menuManager.add(new Separator());
				menuManager.add(new Action(
						UIText.CommitFileDiffViewer_CompareMenuLabel) {

					@Override
					public void run() {
						FileDiffRegion fileDiff = selected.iterator().next();
						DiffViewer.showTwoWayFileDiff(fileDiff.getDiff());
					}
				});
			}
