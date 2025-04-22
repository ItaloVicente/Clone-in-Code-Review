		Control control = branchViewer.getControl();
		if (control != null) {
			control.setData(FormToolkit.KEY_DRAW_BORDER,
					FormToolkit.TREE_BORDER);
			GridDataFactory.fillDefaults().grab(true, true)
					.hint(SWT.DEFAULT, 50).applyTo(control);
			addToFocusTracking(control);
		}
