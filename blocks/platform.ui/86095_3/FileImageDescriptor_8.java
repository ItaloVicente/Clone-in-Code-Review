			if (logIOException) {
				Policy.logException(e);
			} else if (InternalPolicy.DEBUG_LOG_URL_IMAGE_DESCRIPTOR_MISSING_2x) {
				if (name.endsWith("@2x.png") || name.endsWith("@1.5x.png")) { //$NON-NLS-1$ //$NON-NLS-2$
					String message = "High-resolution image missing: " + location + ' ' + name; //$NON-NLS-1$
					Policy.getLog().log(new Status(IStatus.WARNING, Policy.JFACE, message, e));
				}
			}
