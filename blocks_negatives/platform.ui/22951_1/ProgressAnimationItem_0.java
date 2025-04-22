
			if (exception != null) {
				Status status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID,
						exception.getMessage(), exception);
				getStatusReporter().report(status,
				        StatusReporter.LOG | StatusReporter.SHOW, null);
			}

