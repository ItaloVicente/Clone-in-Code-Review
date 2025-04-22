			ToolBar tb = parent.getControl();
			if (tb != null && !tb.isDisposed()) {
				tb.pack(true);
				if (tb.getParent() != null) {
					tb.getParent().pack(true);
				}
				tb.getShell().layout(new Control[] { tb }, SWT.DEFER);
