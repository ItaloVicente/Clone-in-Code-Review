		int currenOrientation = (itemStyle & SWT.VERTICAL) != 0 ? SWT.VERTICAL : SWT.HORIZONTAL;
		return createControl(parent, currenOrientation);
	}

	public ToolBar createControl(Composite parent, int orientation) {
		if (parent != null) {
			boolean orientationChanged = (itemStyle & orientation) == 0;
			if (toolBarExist() && (parent != toolBar.getParent() || orientationChanged)) {
				dispose();
			}

			if (!toolBarExist()) {
				if (orientationChanged) {
					itemStyle &= ~SWT.HORIZONTAL & ~SWT.VERTICAL;
					itemStyle |= orientation;
				}
				toolBar = new ToolBar(parent, itemStyle);
				toolBar.setMenu(getContextMenuControl());
				update(true);

				toolBar.getAccessible().addAccessibleListener(getAccessibleListener());
			}
