                toolBarManager = null;
            }
            if (statusLineManager != null) {
                statusLineManager.dispose();
                statusLineManager = null;
            }
            if (coolBarManager != null) {
            	if (coolBarManager instanceof ICoolBarManager2) {
