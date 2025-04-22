		WorkbenchWindowConfigurer winConfigurer = ww.getWindowConfigurer();
		Transfer[] editorSiteTransfers = winConfigurer.getTransfers();
		DropTargetListener editorSiteListener = winConfigurer.getDropTargetListener();

		MergedDropTarget newTarget = new MergedDropTarget(control, ops, transfers, listener, editorSiteOps,
				editorSiteTransfers, editorSiteListener);
