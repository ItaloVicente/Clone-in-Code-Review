	@Override
	public void create() {
		super.create();
		Job job = new Job(UIText.CreateTagDialog_GetTagJobName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				final List<RevTag> tags = getRevTags();
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (!tagViewer.getTable().isDisposed()) {
							tagViewer.setInput(tags);
							tagViewer.getTable().setEnabled(true);
						}
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}

