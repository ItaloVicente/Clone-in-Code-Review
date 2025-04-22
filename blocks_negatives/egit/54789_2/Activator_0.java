			for (IProject p : toRefresh) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				ISchedulingRule rule = p.getWorkspace().getRuleFactory().refreshRule(p);
				try {
					getJobManager().beginRule(rule, monitor);
					if (p.isAccessible()) {
						p.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 1));
