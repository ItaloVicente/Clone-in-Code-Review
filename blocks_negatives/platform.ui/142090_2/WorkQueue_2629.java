                pendingWorkSet.remove(next);
            }

            next.run();
        }

    }

    /**
     * Schedules some work to happen in the UI thread as soon as possible. If
     * possible, the work will happen before the next control redraws. The given
     * runnable will only be run once. Has no effect if this runnable has
     * already been queued for execution.
     *
     * @param work
     *            runnable to execute
     */
    public void runOnce(Runnable work) {
        synchronized (pendingWork) {
            if (pendingWorkSet.contains(work)) {
                return;
            }

            pendingWorkSet.add(work);

            asyncExec(work);
        }
    }

    /**
     * Schedules some work to happen in the UI thread as soon as possible. If
     * possible, the work will happen before the next control redraws. Unlike
     * runOnce, calling asyncExec twice with the same runnable will cause that
     * runnable to run twice.
     *
     * @param work
     *            runnable to execute
     */
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

    /**
     * Cancels a previously-scheduled runnable. Has no effect if the given
     * runnable was not previously scheduled or has already executed.
     *
     * @param toCancel
     *            runnable to cancel
     */
    public void cancelExec(Runnable toCancel) {
        synchronized (pendingWork) {
            pendingWork.remove(toCancel);
            pendingWorkSet.remove(toCancel);
        }
    }

    /**
     * Cancels all pending work.
     */
    public void cancelAll() {
        synchronized (pendingWork) {
            pendingWork.clear();
            pendingWorkSet.clear();
        }
    }
