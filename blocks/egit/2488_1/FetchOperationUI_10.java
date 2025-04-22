				if (event.getResult().isOK())
					FetchResultDialog.show(repository, op.getOperationResult(),
							sourceString);
				else
					Activator.handleError(event.getResult().getMessage(), event
							.getResult().getException(), true);
