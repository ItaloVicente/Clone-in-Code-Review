        IRunnableWithProgress runnable = new IRunnableWithProgress() {
            @Override
			public void run(final IProgressMonitor monitor)
                    throws InterruptedException {
                monitor
                        .beginTask(
                                DataTransferMessages.ImportPage_filterSelections, IProgressMonitor.UNKNOWN);
                getSelectedResources(filter, monitor);
            }
        };
