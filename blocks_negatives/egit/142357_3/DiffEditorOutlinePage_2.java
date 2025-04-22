					menuManager.add(new Action(
							UIText.CommitFileDiffViewer_CompareMenuLabel) {

						@Override
						public void run() {
							FileDiffRegion fileDiff = selected.iterator()
									.next();
							DiffViewer.showTwoWayFileDiff(fileDiff.getDiff());
						}
					});
