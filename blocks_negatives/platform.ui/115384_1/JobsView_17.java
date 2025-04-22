			getSite().getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						ResourcesPlugin.getWorkspace().run(
								new IWorkspaceRunnable() {
									@Override
									public void run(IProgressMonitor monitor) {
									}
								}, null);
					} catch (OperationCanceledException e) {
					} catch (CoreException e) {
						e.printStackTrace();
					}
