			if (!parentsToRefresh.isEmpty()) {
				this.viewer.getTree().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						for (IContainer parent : parentsToRefresh) {
							NestedProjectsContentProvider.this.viewer.refresh(parent);
						}
