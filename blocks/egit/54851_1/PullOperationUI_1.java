							UIText.PullOperationUI_ConnectionProblem, status);
				} else if (status.getException() instanceof InvalidConfigurationException) {
					ErrorDialog.openError(shell,
							UIText.PullOperationUI_PullFailed,
							UIText.PullOperationUI_ConfigurationProblem, status);
				} else if ((status.getException() instanceof org.eclipse.jgit.api.errors.TransportException)
						&& ("Nothing to fetch.".equalsIgnoreCase(status //$NON-NLS-1$
								.getException().getMessage()))) {
					ErrorDialog.openError(shell,
							UIText.PullOperationUI_PullFailed,
							UIText.PullOperationUI_NothingToFetch, status);
