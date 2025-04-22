				mergeStatus(multiStatus, new Status(IStatus.ERROR,
						Policy.JFACE_DATABINDING, IStatus.ERROR, message,
						ex));
			} finally {
				if (!destinationRealmReached) {
					setValidationStatus(multiStatus);
