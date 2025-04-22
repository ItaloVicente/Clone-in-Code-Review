			int style = itemStyle;
			if ((style & orientation) == 0) {
				style &= ~SWT.HORIZONTAL & ~SWT.VERTICAL;
				style |= orientation;
			}
			toolBar = new ToolBar(parent, style);
