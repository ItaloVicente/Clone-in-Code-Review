		comp.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}

		});

		perspSwitcherToolbar.addMenuDetectListener(new MenuDetectListener() {
			@Override
			public void menuDetected(MenuDetectEvent e) {
				ToolBar tb = (ToolBar) e.widget;
				Point p = new Point(e.x, e.y);
				p = perspSwitcherToolbar.getDisplay().map(null, perspSwitcherToolbar, p);
				ToolItem item = tb.getItem(p);
				if (item == null)
				else {
					MPerspective persp = (MPerspective) item.getData();
					if (persp == null)
					else
						openMenuFor(item, persp);
				}
