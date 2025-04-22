				PushOperationResult result = op.getOperationResult();
				if (expectedResult == null || !expectedResult.equals(result)) {
					if (event.getResult().isOK())
						PushResultDialog.show(repository, result,
								destinationString, showConfigureButton);
					else
						Activator.handleError(event.getResult().getMessage(),
								event.getResult().getException(), true);
				}
