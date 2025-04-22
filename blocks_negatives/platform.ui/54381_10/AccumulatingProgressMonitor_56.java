        display.asyncExec(new Runnable() {
            @Override
			public void run() {
                ((IProgressMonitorWithBlocking) pm).setBlocked(reason);
                Dialog.getBlockedHandler().showBlocked(pm, reason, currentTask);
            }
        });
