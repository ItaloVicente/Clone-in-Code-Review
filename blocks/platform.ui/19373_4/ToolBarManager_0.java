		int currenOrientation = (itemStyle & SWT.VERTICAL) != 0 ? SWT.VERTICAL : SWT.HORIZONTAL;
		return createControl(parent, currenOrientation);
	}

	public ToolBar createControl(Composite parent, int orientation) {
		if (parent != null) {
			if (toolBarExist() && (parent != toolBar.getParent()
					|| (orientation & toolBar.getStyle()) == 0)) {
				dispose();
			}

			if (!toolBarExist()) {
				int style = itemStyle;
				if ((style & orientation) == 0) {
					style &= ~SWT.HORIZONTAL & ~SWT.VERTICAL;
					style |= orientation;
				}
				toolBar = new ToolBar(parent, style);
				toolBar.setMenu(getContextMenuControl());
				update(true);

				toolBar.getAccessible().addAccessibleListener(getAccessibleListener());
			}
