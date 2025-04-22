			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					ProgressMonitorDialog dialog = new ProgressMonitorDialog(new Shell());
					try {
						dialog.run(true, false, new WorkspaceModifyOperation() {
							@Override
							protected void execute(IProgressMonitor monitor) {}
						});
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						fail(e.getMessage());
					} catch (InterruptedException e) {
					}
