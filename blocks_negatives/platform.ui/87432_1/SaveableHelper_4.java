		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(monitor);
				saveable.doSave(monitorWrap);
			}
