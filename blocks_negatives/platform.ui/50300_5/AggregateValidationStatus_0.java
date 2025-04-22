	public static IStatus getStatusMerged(Collection validationStatusProviders) {
		List statuses = new ArrayList();
		for (Iterator it = validationStatusProviders.iterator(); it.hasNext();) {
			ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider) it
					.next();
			IStatus status = (IStatus) validationStatusProvider
					.getValidationStatus().getValue();
