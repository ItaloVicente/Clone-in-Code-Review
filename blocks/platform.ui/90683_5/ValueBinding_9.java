				});
			} catch (Exception ex) {
				String message = (ex.getMessage() != null) ? ex.getMessage() : ""; //$NON-NLS-1$

				mergeStatus(multiStatus,
						new Status(IStatus.ERROR, Policy.JFACE_DATABINDING, IStatus.ERROR, message, ex));
			} finally {
				if (!destinationRealmReached) {
					setValidationStatus(multiStatus);
