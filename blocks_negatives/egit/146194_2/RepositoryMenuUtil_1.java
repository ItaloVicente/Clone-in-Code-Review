		@Override
		public void run() {
			showMenu = true;
		}

		@Override
		public void runWithEvent(Event event) {
			if (!isEnabled()) {
				return;
			}
			showMenu = false;
			run();
			Widget widget = event.widget;
			if (showMenu && (widget instanceof ToolItem)) {
				ToolItem item = (ToolItem) widget;
				Rectangle bounds = item.getBounds();
				event.detail = SWT.ARROW;
				event.x = bounds.x;
				event.y = bounds.y + bounds.height;
				item.notifyListeners(SWT.Selection, event);
			}
		}
