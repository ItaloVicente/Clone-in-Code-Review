				FileDiff diff = haveNew.get(0).getDiff();
				RevCommit base = diff.getBase();
				String title;
				if (base == null
						|| base.equals(diff.getCommit().getParent(0))) {
					title = UIText.CommitFileDiffViewer_CompareMenuLabel;
				} else {
					title = UIText.CommitFileDiffViewer_CompareSideBySideMenuLabel;
				}
