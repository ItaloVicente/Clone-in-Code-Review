			if (toolbarManager instanceof ToolBarManager) {
				ToolBarManager tbm = (ToolBarManager) toolbarManager;
				Control tbCtrl = tbm.getControl();
				if (tbCtrl == null || tbCtrl.isDisposed()) {
					if (part.getContext() != null) {
					}
				} else {
					tbm.update(true);
					if (!tbCtrl.isDisposed()) {
						Control packParent = getPackParent(tbCtrl);
						packParent.pack();

						if (packParent.getParent() instanceof CTabFolder)
							packParent.getParent().layout(true);
					}
