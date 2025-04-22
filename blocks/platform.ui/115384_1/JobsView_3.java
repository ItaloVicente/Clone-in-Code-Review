			getSite().getShell().getDisplay().asyncExec(() -> {
				try {
					ResourcesPlugin.getWorkspace().run((IWorkspaceRunnable) monitor -> {
					}, null);
				} catch (OperationCanceledException e1) {
				} catch (CoreException e2) {
					e2.printStackTrace();
