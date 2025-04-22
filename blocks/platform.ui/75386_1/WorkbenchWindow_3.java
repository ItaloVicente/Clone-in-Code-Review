				IProgressMonitor pm = CancelabilityMonitorUtils.aboutToStart(cancelable, manager.getProgressMonitor(),
						runnable.getClass().getName());
				BusyIndicator.showWhile(getShell().getDisplay(), () -> {
					try {
						ModalContext.run(runnable, fork, pm, getShell().getDisplay());
					} catch (InvocationTargetException e) {
						ite[0] = e;
					} catch (InterruptedException e) {
						ie[0] = e;
					} finally {
						manager.getProgressMonitor().done();
						CancelabilityMonitorUtils.hasStopped(pm);
