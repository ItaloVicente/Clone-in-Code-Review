        uiSynchronize.asyncExec(new Runnable() {
            @Override
			public void run() {
                IStatus result = null;
                Throwable throwable = null;
                try {
                    setThread(Thread.currentThread());
                    if (monitor.isCanceled()) {
						result = Status.CANCEL_STATUS;
					} else {
                        result = runInUIThread(monitor);
                    }

                } catch(Throwable t){
                	throwable = t;
                } finally {
                    if (result == null) {
						result = new Status(IStatus.ERROR,
                                IProgressConstants.PLUGIN_ID, IStatus.ERROR,
                                ProgressMessages.InternalError,
                                throwable);
                    }
                    done(result);
                }
            }
        });
