	private static class IgnoreFoldersAction extends Action {
		private final Set<StagingFolderEntry> selection;

		IgnoreFoldersAction(Set<StagingFolderEntry> selection) {
			super(UIText.StagingView_IgnoreFolderMenuLabel);
			this.selection = selection;
		}

		@Override
		public void run() {
			List<IPath> paths = new ArrayList<>();
			for (StagingFolderEntry folder : selection) {
				paths.add(folder.getLocation());
			}
			IgnoreOperationUI operation = new IgnoreOperationUI(paths);
			operation.run();
		}

	}

