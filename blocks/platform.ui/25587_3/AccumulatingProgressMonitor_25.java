        synchronized (this) {
            collector = null;
        }
        display.asyncExec(new Runnable() {
            @Override
			public void run() {
                currentTask = name;
                getWrappedProgressMonitor().setTaskName(name);
            }
        });
    }
