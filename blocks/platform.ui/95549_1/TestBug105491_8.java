			Display.getDefault().asyncExec(() -> {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
				try {
					dialog.run(true, false, new WorkspaceModifyOperation() {
						@Override
						protected void execute(IProgressMonitor monitor) {
						}
					});
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
					fail(e1.getMessage());
				} catch (InterruptedException e2) {
