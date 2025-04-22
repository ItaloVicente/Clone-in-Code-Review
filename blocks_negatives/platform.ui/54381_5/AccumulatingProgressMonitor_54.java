        display.asyncExec(new Runnable() {
            @Override
			public void run() {
                getWrappedProgressMonitor().done();
            }
        });
