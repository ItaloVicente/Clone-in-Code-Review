                if (container != null) {
                    IProgressMonitor monitor = container.getActionBars()
                            .getStatusLineManager().getProgressMonitor();
                    monitor.done();
                }
                if (showToolbar) {
                    loading = false;
                    updateBackNextBusy();
                    updateHistory();
                }
            }
        });

        if (showURLbar) {
