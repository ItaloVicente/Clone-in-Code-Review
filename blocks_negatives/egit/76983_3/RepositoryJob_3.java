		IStatus status = performJob(monitor);
		if (status == null) {
			return Activator
					.createErrorStatus(
							MessageFormat.format(
									UIText.RepositoryJob_NullStatus, getName()),
							new NullPointerException());
		} else if (!status.isOK()) {
			return status;
		}
		IAction action = getAction();
		if (action != null) {
			if (isModal()) {
				showResult(action);
			} else {
				setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
				setProperty(IProgressConstants.ACTION_PROPERTY, action);
				return new Status(IStatus.OK, Activator.getPluginId(),
						IStatus.OK, action.getText(), null);
