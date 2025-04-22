			final int style = item.getStyle();
			if (((style & SWT.DROP_DOWN) != 0) && (event.detail == SWT.ARROW) && (item instanceof ToolItem)) {
				final ToolItem toolItem = (ToolItem) item;
				final ToolBar toolBar = toolItem.getParent();
				if (loadDelegate() && (delegate instanceof IWorkbenchWindowPulldownDelegate2)) {
					final IWorkbenchWindowPulldownDelegate2 delegate2 = (IWorkbenchWindowPulldownDelegate2) delegate;
					final MenuLoader loader = new MenuLoader(delegate2, toolBar);
					SafeRunner.run(loader);
					final Menu subMenu = loader.getMenu();
					if (subMenu != null) {
						final Rectangle bounds = toolItem.getBounds();
						final Point location = toolBar.toDisplay(new Point(bounds.x, bounds.y + bounds.height));
						subMenu.setLocation(location);
						subMenu.setVisible(true);
					}
