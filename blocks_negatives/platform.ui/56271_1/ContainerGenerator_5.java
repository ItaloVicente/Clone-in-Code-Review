            projectHandle.open(new SubProgressMonitor(monitor, 1000));
            if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
        } finally {
            monitor.done();
        }
