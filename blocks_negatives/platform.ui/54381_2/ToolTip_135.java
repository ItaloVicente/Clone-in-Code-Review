		this.shellListener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				if (ToolTip.this.control != null
						&& !ToolTip.this.control.isDisposed()) {
					ToolTip.this.control.getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (ToolTip.this.control != null && !ToolTip.this.control.isDisposed()
									&& ToolTip.this.control.getDisplay().getActiveShell() != CURRENT_TOOLTIP) {
								toolTipHide(CURRENT_TOOLTIP, event);
							}
						}

					});
				}
