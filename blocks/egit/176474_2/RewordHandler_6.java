					IStatus status = e.getStatus();
					if (status
							.getException() instanceof GpgConfigurationException) {
						gpgConfigProblem = e.getStatus();
						return Status.OK_STATUS;
					}
					return status;
				} finally {
					monitor.done();
