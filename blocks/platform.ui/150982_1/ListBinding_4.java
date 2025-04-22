			} finally {
				setValidationStatus(multiStatus);

				if (destination == target) {
					updatingTarget = false;
				} else {
					updatingModel = false;
				}
