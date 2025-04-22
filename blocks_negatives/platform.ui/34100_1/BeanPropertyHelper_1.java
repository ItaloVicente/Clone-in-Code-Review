			if (BeansObservables.DEBUG) {
				Policy
						.getLog()
						.log(
								new Status(
										IStatus.WARNING,
										Policy.JFACE_DATABINDING,
										IStatus.OK,
										"Could not read value of " + source + "." + propertyDescriptor.getName(), e)); //$NON-NLS-1$ //$NON-NLS-2$
			}
