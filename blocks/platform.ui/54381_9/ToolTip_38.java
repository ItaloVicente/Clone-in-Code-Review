		this.shellListener = event -> {
			if (ToolTip.this.control != null
					&& !ToolTip.this.control.isDisposed()) {
				ToolTip.this.control.getDisplay().asyncExec(() -> {
					if (ToolTip.this.control != null && !ToolTip.this.control.isDisposed()
							&& ToolTip.this.control.getDisplay().getActiveShell() != CURRENT_TOOLTIP) {
						toolTipHide(CURRENT_TOOLTIP, event);
					}
				});
