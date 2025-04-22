				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						try {
							workspace.run(new IWorkspaceRunnable() {
								@Override
								public void run(IProgressMonitor mon) throws CoreException {
									project.touch(null);
									try {
										Thread.sleep(timeToSleep);
									} catch (InterruptedException ex) {
										ex.printStackTrace();
									}
								}
							}, workspace.getRoot(), IResource.NONE, pm);
							workspace.run(new IWorkspaceRunnable() {
								@Override
								public void run(IProgressMonitor mon) {
								}
							}, pm);

						} catch (CoreException ex) {
							ex.printStackTrace();
						}
