		treeViewer.getControl().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				refreshJob.cancel();
			}
		});
