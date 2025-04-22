		} else {
			ToolBar toolBar = manager.getControl();
			if (toolBar != null && !toolBar.isDisposed()
					&& (toolBar.getStyle() & orientation) == 0) {
				manager.dispose();
			}
			manager.setStyle(style);
