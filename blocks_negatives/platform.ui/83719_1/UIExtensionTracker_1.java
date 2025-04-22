        display.syncExec(new Runnable() {
            @Override
			public void run() {
                try {
                    handler.addExtension(UIExtensionTracker.this, addedExtension);
                } catch (Exception e) {
                    WorkbenchPlugin.log(getClass(), "doAdd", e); //$NON-NLS-1$
                }
