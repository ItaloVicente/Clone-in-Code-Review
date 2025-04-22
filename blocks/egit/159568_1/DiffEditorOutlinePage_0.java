				RevCommit commit = haveNew.get(0).getDiff().getCommit();
				String title = MessageFormat.format(
						UIText.CommitFileDiffViewer_OpenInEditorMenuWithCommitLabel,
						Utils.getShortObjectId(commit));
				String tooltip = MessageFormat.format(
						UIText.CommitFileDiffViewer_OpenInEditorMenuTooltip,
						UIUtils.menuText(commit.getShortMessage(), 80));
				IAction action = new Action(title) {
