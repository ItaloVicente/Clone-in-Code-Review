				final SubProgressMonitor subMonitor = new SubProgressMonitor(
						monitor, WORK_UNITS_PER_TRANSPORT,
						SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);

				try {
					if (monitor.isCanceled()) {
