        IRunnableWithProgress runnable = monitor -> {
            monitor
		    .beginTask(
		            DataTransferMessages.ImportPage_filterSelections, IProgressMonitor.UNKNOWN);
            getSelectedResources(filter, monitor);
         };
