			if (e.getCause() instanceof GpgConfigurationException) {
				showGpgProblem(e.getStatus());
				return Status.CANCEL_STATUS;
			} else if (e.getCause() instanceof JGitInternalException) {
				if (e.getCause()
						.getCause() instanceof GpgConfigurationException) {
					showGpgProblem(e.getStatus());
					return Status.CANCEL_STATUS;
				}
