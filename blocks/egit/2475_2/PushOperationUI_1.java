				if (event.getResult().isOK())
					PushResultDialog.show(repository, op.getOperationResult(),
							destinationString);
				else
					Activator.handleError(event.getResult().getMessage(), event
							.getResult().getException(), true);
