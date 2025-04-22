		control.addDisposeListener(this);
	}

	public void dispose() {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				synchronized (SWTCommitList.this) {
					clear();
				}
				return Status.OK_STATUS;
			}
		};
		clearJob.setSystem(true);
		clearJob.schedule();

		if (!control.isDisposed())
			control.removeDisposeListener(this);
