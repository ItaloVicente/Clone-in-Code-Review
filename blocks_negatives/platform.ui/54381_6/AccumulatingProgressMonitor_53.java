        display.asyncExec(new Runnable() {
            @Override
			public void run() {
                currentTask = name;
                getWrappedProgressMonitor().beginTask(name, totalWork);
            }
        });
