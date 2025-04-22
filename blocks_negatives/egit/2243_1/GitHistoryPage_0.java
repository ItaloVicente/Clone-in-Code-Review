
	enum ShowFilter {
		SHOWALLRESOURCE, SHOWALLFOLDER, SHOWALLPROJECT, SHOWALLREPO,
	}

	class ShowFilterAction extends Action {
		private final ShowFilter filter;

		ShowFilterAction(ShowFilter filter, ImageDescriptor icon,
				String menuLabel, String toolTipText) {
			super(null, IAction.AS_CHECK_BOX);
			this.filter = filter;
			setImageDescriptor(icon);
			setText(menuLabel);
			setToolTipText(toolTipText);
		}

		@Override
		public void run() {
			String oldName = getName();
			String oldDescription = GitHistoryPage.this.getDescription();
			if (!isChecked()) {
				if (showAllFilter == filter) {
					showAllFilter = ShowFilter.SHOWALLRESOURCE;
					showAllResourceVersionsAction.setChecked(true);
					initAndStartRevWalk(false);
				}
			}
			if (isChecked() && showAllFilter != filter) {
				showAllFilter = filter;
				if (this != showAllRepoVersionsAction)
					showAllRepoVersionsAction.setChecked(false);
				if (this != showAllProjectVersionsAction)
					showAllProjectVersionsAction.setChecked(false);
				if (this != showAllFolderVersionsAction)
					showAllFolderVersionsAction.setChecked(false);
				if (this != showAllResourceVersionsAction)
					showAllResourceVersionsAction.setChecked(false);
				initAndStartRevWalk(false);
			}
			GitHistoryPage.this.firePropertyChange(GitHistoryPage.this, P_NAME,
					oldName, getName());
			GitHistoryPage.this.firePropertyChange(GitHistoryPage.this,
					P_DESCRIPTION, oldDescription, GitHistoryPage.this
							.getDescription());
			Activator.getDefault().getPreferenceStore().setValue(
					PREF_SHOWALLFILTER, showAllFilter.toString());
		}

		@Override
		public String toString() {
		}
	}

	private ShowFilter showAllFilter = ShowFilter.SHOWALLRESOURCE;

	private ShowFilterAction showAllRepoVersionsAction;

	private ShowFilterAction showAllProjectVersionsAction;

	private ShowFilterAction showAllFolderVersionsAction;

	private ShowFilterAction showAllResourceVersionsAction;

