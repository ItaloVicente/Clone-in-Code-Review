        display.asyncExec(new Runnable() {
            @Override
			public void run() {
                ((IProgressMonitorWithBlocking) pm).clearBlocked();
                Dialog.getBlockedHandler().clearBlocked();
            }
        });
