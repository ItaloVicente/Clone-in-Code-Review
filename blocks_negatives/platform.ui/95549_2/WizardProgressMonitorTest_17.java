		return new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {

				assertEquals(
						"The progress monitor's label is not initially empty", //$NON-NLS-1$
						"", dialog.getProgressMonitorLabelText()); //$NON-NLS-1$

				String subTask = dialog.getProgressMonitorSubTaskText();
				if(subTask !=null && subTask.length() != 0)
				 {
				}

				monitor.beginTask(taskName, 1);
