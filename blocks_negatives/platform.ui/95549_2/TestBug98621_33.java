			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						workspace.run(new IWorkspaceRunnable() {
							@Override
							public void run(IProgressMonitor mon) {
							}
						}, workspace.getRoot(), IResource.NONE, null);
					} catch (CoreException ex) {
						ex.printStackTrace();
					}
