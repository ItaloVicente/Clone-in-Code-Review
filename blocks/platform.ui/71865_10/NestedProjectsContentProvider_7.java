		}
		if (!parentsToRefresh.isEmpty()) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (viewer.getTree() == null || viewer.getTree().isDisposed()) {
						return;
