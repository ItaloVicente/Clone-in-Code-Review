				toolBarManager.removeAll();
				toolBarManager = null;
			}
			if (statusLineManager != null) {
				statusLineManager.dispose();
				statusLineManager.removeAll();
				statusLineManager = null;
			}
			if (coolBarManager != null) {
				if (coolBarManager instanceof ICoolBarManager2) {
