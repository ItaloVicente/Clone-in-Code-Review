						});
					} finally {
						setValidationStatus(multiStatus);

						if (destination == getTarget()) {
							updatingTarget = false;
						} else {
							updatingModel = false;
