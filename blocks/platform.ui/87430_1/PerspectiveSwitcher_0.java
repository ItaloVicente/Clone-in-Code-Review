		comp.addDisposeListener(e -> dispose());

		perspSwitcherToolbar.addMenuDetectListener(e -> {
			ToolBar tb = (ToolBar) e.widget;
			Point p = new Point(e.x, e.y);
			p = perspSwitcherToolbar.getDisplay().map(null, perspSwitcherToolbar, p);
			ToolItem item = tb.getItem(p);
			if (item == null)
				E4Util.message("  ToolBar menu"); //$NON-NLS-1$
			else {
				MPerspective persp = (MPerspective) item.getData();
				if (persp == null)
					E4Util.message("  Add button Menu"); //$NON-NLS-1$
				else
					openMenuFor(item, persp);
