        synchronized (this) {
            collector = null;
        }
        display.asyncExec(() -> {
		    currentTask = name;
		    getWrappedProgressMonitor().beginTask(name, totalWork);
