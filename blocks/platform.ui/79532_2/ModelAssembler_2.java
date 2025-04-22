		Diagnostic validationResult = Diagnostician.INSTANCE.validate((EObject) fragment);
		int severity = validationResult.getSeverity();
		if (severity == Diagnostic.ERROR) {
			logger.error(
					"Fragment from \"" + "uri.toString()" + "\" of \"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ contributorName + "\" could not be validated and was not merged \"{0}\"", //$NON-NLS-1$
					fragment.toString());
		}
