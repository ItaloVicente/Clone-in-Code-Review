				pendingWorkSet.remove(next);
			}

			next.run();
		}

	}

	public void runOnce(Runnable work) {
		synchronized (pendingWork) {
			if (pendingWorkSet.contains(work)) {
				return;
			}

			pendingWorkSet.add(work);

			asyncExec(work);
		}
	}

	public void asyncExec(Runnable work) {
		synchronized (pendingWork) {
			pendingWork.add(work);
			if (!updateScheduled) {
				updateScheduled = true;
				d.asyncExec(updateJob);
			}

			if (Display.getCurrent() == d) {
				if (!paintListenerAttached) {
					paintListenerAttached = true;
					d.addFilter(SWT.Paint, paintListener);
				}
			}
		}
	}

	public void cancelExec(Runnable toCancel) {
		synchronized (pendingWork) {
			pendingWork.remove(toCancel);
			pendingWorkSet.remove(toCancel);
		}
	}

	public void cancelAll() {
		synchronized (pendingWork) {
			pendingWork.clear();
			pendingWorkSet.clear();
		}
	}
