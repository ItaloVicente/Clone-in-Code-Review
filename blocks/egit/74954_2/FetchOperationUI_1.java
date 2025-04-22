
	private static class ShowResultAction extends RepositoryJobResultAction {

		private final FetchResult result;

		private final String source;

		public ShowResultAction(@NonNull Repository repository,
				FetchResult result, String source) {
			super(repository, UIText.FetchOperationUI_ShowFetchResult);
			this.result = result;
			this.source = source;
		}

		@Override
		protected void showResult(@NonNull Repository repository) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell();
			FetchResultDialog dialog = new FetchResultDialog(shell, repository,
					result, source);
			dialog.open();
		}
	}

