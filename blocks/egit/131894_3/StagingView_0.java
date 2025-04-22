	private void workaroundMissingSwtRefresh(TreeViewer viewer) {
		if (Util.isWindows()) {
			viewer.getControl().redraw();
		}
	}

