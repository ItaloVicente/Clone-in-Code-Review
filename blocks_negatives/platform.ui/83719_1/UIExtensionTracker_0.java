		display.syncExec(new Runnable() {

            @Override
			public void run() {
                try {
                    handler.removeExtension(removedExtension, objects);
                } catch (Exception e) {
                    WorkbenchPlugin.log(getClass(), "doRemove", e); //$NON-NLS-1$
                }
