
	private static class SourceSelectionDialog extends
			AbstractBranchSelectionDialog {

		public SourceSelectionDialog(Shell parentShell, Repository repository,
				String refToMark) {
			super(parentShell, repository, refToMark, SHOW_LOCAL_BRANCHES
					| SHOW_REMOTE_BRANCHES | SHOW_TAGS | SHOW_REFERENCES
					| SELECT_CURRENT_REF | EXPAND_LOCAL_BRANCHES_NODE
					| EXPAND_REMOTE_BRANCHES_NODE);
		}

		@Override
		protected void refNameSelected(String refName) {
			setOkButtonEnabled(refName != null);
		}

		@Override
		protected String getTitle() {
			return UIText.CreateBranchPage_SourceSelectionDialogTitle;
		}

		@Override
		protected String getMessageText() {
			return UIText.CreateBranchPage_SourceSelectionDialogMessage;
		}
	}

