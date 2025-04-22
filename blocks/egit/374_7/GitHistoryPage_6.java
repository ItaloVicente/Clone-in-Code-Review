	private class CreatePatchAction extends Action {

		private TreeWalk walker;

		public CreatePatchAction() {
			super(UIText.GitHistoryPage_CreatePatch);
		}

		@Override
		public void run() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			if (selection.size() == 1) {

				Iterator<?> it = selection.iterator();
				SWTCommit commit = (SWTCommit) it.next();

				GitCreatePatchWizard.run(getHistoryPageSite().getPart(),
						commit, walker, db);
			}
		}

		public void setTreeWalk(TreeWalk walker) {
			this.walker = walker;
		}

		@Override
		public boolean isEnabled() {
			IStructuredSelection selection = ((IStructuredSelection) revObjectSelectionProvider
					.getSelection());
			Iterator<?> it = selection.iterator();
			SWTCommit commit = (SWTCommit) it.next();
			return (commit.getParentCount() == 1);

		}

	}
