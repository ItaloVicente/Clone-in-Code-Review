								mergeStatus(multiStatus, setterStatus);
							} finally {
								if (destination == target) {
									updatingTarget = false;
								} else {
									updatingModel = false;
								}
								setValidationStatus(multiStatus);
							}
