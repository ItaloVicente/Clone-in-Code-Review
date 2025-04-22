		ToolItem ti = null;
		if (index >= 0) {
			ti = new ToolItem(parent, flags, index);
		} else {
			ti = new ToolItem(parent, flags);
		}
		ti.setData(this);
		ti.addListener(SWT.Selection, getToolItemListener());
		ti.addListener(SWT.Dispose, getToolItemListener());
