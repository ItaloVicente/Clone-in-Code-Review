			Diagnostic validationResult = Diagnostician.INSTANCE.validate((EObject) fragment);
			int severity = validationResult.getSeverity();
			if (severity == Diagnostic.ERROR) {
				logger.error("Fragment from \"" + uri.toString() + "\" of \"" + bundleName // $NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-2$
						+ "\" could not be validated and will not be merged \"{0}\"", fragment); // $NON-NLS-1$ //$NON-NLS-1$
				continue;
			}
