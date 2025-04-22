				FileDiff diff = haveOld.get(0).getDiff();
				RevCommit commit = diff.getCommit();
				RevCommit base = diff.getBase();
				String msg;
				if (base == null || base.equals(commit.getParent(0))) {
					msg = UIText.CommitFileDiffViewer_OpenPreviousInEditorMenuWithCommitLabel;
					if (base == null) {
						base = commit.getParent(0);
					}
				} else {
					msg = UIText.CommitFileDiffViewer_OpenBaseInEditorMenuWithCommitLabel;
				}

				String title = MessageFormat.format(msg,
						Utils.getShortObjectId(base));
				String tooltip = MessageFormat.format(
						UIText.CommitFileDiffViewer_OpenInEditorMenuTooltip,
						UIUtils.menuText(base.getShortMessage(), 80));
				IAction action = new Action(title) {
