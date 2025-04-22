		workspace.run(new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
				try {
					dialog.run(true, false, new TransferTestOperation());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					fail(e.getMessage());
				} catch (InterruptedException e) {
				}
