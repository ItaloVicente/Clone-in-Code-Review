			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (viewer.getTree() == null || viewer.getTree().isDisposed()) {
						return;
					}
					for (IContainer parent : parentsToRefresh) {
						NestedProjectsContentProvider.this.viewer.refresh(parent);
					}
