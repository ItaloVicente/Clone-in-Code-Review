			PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
				if (viewer.getTree() == null || viewer.getTree().isDisposed()) {
					return;
				}
				for (IContainer parent : parentsToRefresh) {
					viewer.refresh(parent);
