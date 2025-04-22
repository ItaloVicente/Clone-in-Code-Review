
			parent.markDirty();
			parent.update(true);
			ToolBar tb = parent.getControl();
			if (tb != null && !tb.isDisposed()) {
				tb.pack(true);
				tb.getShell().layout(new Control[] { tb }, SWT.DEFER);
			}
