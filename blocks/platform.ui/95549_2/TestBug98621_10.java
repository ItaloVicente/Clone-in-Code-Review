			Display.getDefault().asyncExec(() -> {
				try {
					workspace.run((IWorkspaceRunnable) mon -> {
					}, workspace.getRoot(), IResource.NONE, null);
				} catch (CoreException ex) {
					ex.printStackTrace();
