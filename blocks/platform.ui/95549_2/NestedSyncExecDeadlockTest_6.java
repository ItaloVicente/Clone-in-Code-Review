				Display.getDefault().syncExec(() -> {
					try {
						workspace.run((IWorkspaceRunnable) mon -> {
							project.touch(null);
							try {
								Thread.sleep(timeToSleep);
							} catch (InterruptedException ex) {
								ex.printStackTrace();
							}
						}, workspace.getRoot(), IResource.NONE, pm);
						workspace.run((IWorkspaceRunnable) mon -> {
						}, pm);

					} catch (CoreException ex) {
						ex.printStackTrace();
