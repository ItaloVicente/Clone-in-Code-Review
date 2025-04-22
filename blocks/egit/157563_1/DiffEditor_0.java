		DiffJob job = getDiffer(commit, base);
		job.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (!event.getResult().isOK()) {
					return;
				}
				new UIJob(UIText.DiffEditor_TaskUpdatingViewer) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						if (UIUtils
								.isUsable(getSourceViewer().getTextWidget())) {
							input.setDocument(job.getDocument());
							setInput(input);
						}
						return Status.OK_STATUS;
					}
				}.schedule();
			}
		});
		job.schedule();
	}

	public static abstract class DiffJob extends Job {

		private DiffDocument document;

		protected DiffJob() {
			super(UIText.DiffEditor_TaskGeneratingDiff);
		}

		public DiffDocument getDocument() {
			return document;
		}

		public void setDocument(DiffDocument document) {
			this.document = document;
		}
	}

	public static DiffJob getDiffer(@NonNull IRepositoryCommit tip,
			IRepositoryCommit base) {
		return new DiffJob() {
