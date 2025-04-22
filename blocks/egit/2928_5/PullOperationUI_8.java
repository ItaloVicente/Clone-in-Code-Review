				} else
					Activator.handleError(status.getMessage(), status
							.getException(), true);
			}
		} else
			new MultiPullResultDialog(shell, results).open();
