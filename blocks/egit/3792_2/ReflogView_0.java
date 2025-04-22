	public void onRefsChanged(RefsChangedEvent event) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				ReflogView.this.refLogTableTreeViewer.refresh();
			}
		});
	}

