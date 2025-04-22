                if (event.total == 0)
                    return;

                boolean done = (event.current == event.total);

                int percentProgress = event.current * 100 / event.total;
                if (container != null) {
                    IProgressMonitor monitor = container.getActionBars()
                            .getStatusLineManager().getProgressMonitor();
                    if (done) {
                        monitor.done();
                        progressWorked = 0;
                    } else if (progressWorked == 0) {
                        monitor.beginTask("", event.total); //$NON-NLS-1$
                        progressWorked = percentProgress;
                    } else {
                        monitor.worked(event.current - progressWorked);
                        progressWorked = event.current;
                    }
                }

                if (showToolbar) {
                    if (!busy.isBusy() && !done)
                        loading = true;
                        loading = false;

                    updateBackNextBusy();
                    updateHistory();
                }
            }

            @Override
