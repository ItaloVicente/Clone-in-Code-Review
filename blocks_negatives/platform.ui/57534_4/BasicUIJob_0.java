		asyncDisplay.asyncExec(new Runnable() {
			@Override
			public void run() {
				IStatus result = null;
				try {
					setThread(Thread.currentThread());
					if (monitor.isCanceled()) {
						result = Status.CANCEL_STATUS;
					} else {
						result = runInUIThread(monitor);
					}
				} finally {
					done(result);
