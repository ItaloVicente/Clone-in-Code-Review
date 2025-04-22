		try {
			IStatus status = performJob(monitor);
			if (status == null) {
				return Activator.createErrorStatus(MessageFormat
						.format(UIText.RepositoryJob_NullStatus, getName()),
						new NullPointerException());
			} else if (!status.isOK()) {
				return status;
