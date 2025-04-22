			Policy.setLog(new ILogger() {
				@Override
				public void log(IStatus status) {
					if (status != null && status.getSeverity() == IStatus.ERROR && status.getPlugin().equals(Policy.JFACE)) {
						errorLogged[0] = true;
					}
				}} );


	    	Job job = new Job("Non-UI thread FontRegistry Access Test") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					boolean created = checkFont(fontRegistry);
					assertFalse(created);
					return Status.OK_STATUS;
				}
	    	};
			job.schedule();
