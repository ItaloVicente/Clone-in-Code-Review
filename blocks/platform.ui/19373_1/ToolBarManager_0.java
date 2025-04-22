		int currenOrientation = (itemStyle & SWT.VERTICAL) != 0 ? SWT.VERTICAL : SWT.HORIZONTAL;
		return createControl(parent, currenOrientation);
	}

	public ToolBar createControl(Composite parent, int orientation) {
		if (toolBarExist() && parent != null &&
				(parent != toolBar.getParent() || (orientation & toolBar.getStyle()) == 0)) {
			dispose();
		}

