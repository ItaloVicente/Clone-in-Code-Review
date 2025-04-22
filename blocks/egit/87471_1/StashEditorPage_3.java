		Control control = stagedDiffViewer.getControl();
		if (control != null) {
			control.setData(FormToolkit.KEY_DRAW_BORDER,
					FormToolkit.TREE_BORDER);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(control);
			addToFocusTracking(control);
		}
