		if (super.close()) {
			if (menuBarManager != null) {
				menuBarManager.dispose();
				menuBarManager = null;
			}
			if (toolBarManager != null) {
				if (toolBarManager instanceof IToolBarManager2) {
