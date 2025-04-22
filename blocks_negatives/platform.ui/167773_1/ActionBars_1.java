			Control tbCtrl = toolbarManager.getControl();
			if (tbCtrl == null || tbCtrl.isDisposed()) {
				if (part.getContext() != null) {
				}
			} else {
				toolbarManager.update(true);
				if (!tbCtrl.isDisposed()) {
					Control packParent = getPackParent(tbCtrl);
					packParent.pack();
				}
			}
