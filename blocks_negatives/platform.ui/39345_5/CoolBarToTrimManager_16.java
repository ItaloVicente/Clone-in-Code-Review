		final List<MToolBar> children = modelService.findElements(window, null, MToolBar.class,
				null);
		for (MToolBar el : children) {
			if (el instanceof MToolBar) {
				MToolBar toolbar = (MToolBar) el;
				ToolBarManagerRenderer renderer = (ToolBarManagerRenderer) rendererFactory
						.getRenderer(el, null);
				final ToolBarManager manager = renderer.getManager(toolbar);
				if (manager != null) {
					manager.update(true);
					ToolBar tb = manager.getControl();
					if (tb != null && !tb.isDisposed()) {
						tb.getShell().layout(new Control[] { tb }, SWT.DEFER);
					}
