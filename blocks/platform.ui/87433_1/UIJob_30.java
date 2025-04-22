        asyncDisplay.asyncExec(() -> {
		    IStatus result = null;
		    Throwable throwable = null;
		    try {
		        setThread(Thread.currentThread());
		        if (monitor.isCanceled()) {
					result = Status.CANCEL_STATUS;
				} else {
		           	UIStats.start(UIStats.UI_JOB, getName());
		            result = runInUIThread(monitor);
		        }
