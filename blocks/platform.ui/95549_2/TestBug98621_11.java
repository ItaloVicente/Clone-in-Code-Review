		workspace.run((IWorkspaceRunnable) monitor -> {
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
			try {
				dialog.run(true, false, new TransferTestOperation());
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
				fail(e1.getMessage());
			} catch (InterruptedException e2) {
