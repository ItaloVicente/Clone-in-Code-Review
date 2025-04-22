		for (MModelFragment fragment : fragmentsContainer.getFragments()) {
			Diagnostic validationResult = Diagnostician.INSTANCE.validate((EObject) fragment);
			int severity = validationResult.getSeverity();
			if (severity == Diagnostic.ERROR) {
						+ ce.getContributor().getName() + "\" could not be validated and was not merged \"{0}\"", //$NON-NLS-1$
						fragment.toString());

				continue;
			}
