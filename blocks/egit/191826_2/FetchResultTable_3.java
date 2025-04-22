			Object[] commits = getChildren(this);
			if (commits != null) {
				styled.append(
						MessageFormat.format(
								UIText.FetchResultTable_counterCommits,
								Integer.valueOf(commits.length)),
						StyledString.COUNTER_STYLER);
			} else if (!loadingTriggered) {
				loadingTriggered = true;
				((ITreeContentProvider) treeViewer.getContentProvider())
						.getChildren(this);
			}
