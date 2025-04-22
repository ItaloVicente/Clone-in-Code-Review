		Menu menu = menuMgr.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);
		getSite()
				.registerContextMenu(
						"org.eclipse.ui.examples.propertysheet.outline", menuMgr, viewer); //$NON-NLS-1$
	}
