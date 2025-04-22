	public static IStatus getStatusMerged(
			Collection<? extends ValidationStatusProvider> validationStatusProviders) {
		List<IStatus> statuses = new ArrayList<>();
		for (Iterator<? extends ValidationStatusProvider> it = validationStatusProviders
				.iterator(); it.hasNext();) {
			ValidationStatusProvider validationStatusProvider = it.next();
			IStatus status = validationStatusProvider.getValidationStatus()
					.getValue();
