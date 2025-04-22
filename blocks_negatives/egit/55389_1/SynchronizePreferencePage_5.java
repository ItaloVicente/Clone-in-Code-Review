			if (getCorePreferenceStore().needsSaving()) {
				try {
					getCorePreferenceStore().save();
				} catch (IOException e) {
					String message = JFaceResources
							.format("PreferenceDialog.saveErrorMessage", new Object[] { //$NON-NLS-1$
									getTitle(), e.getMessage() });
					Policy.getStatusHandler()
							.show(new Status(IStatus.ERROR, Policy.JFACE,
									message, e),
									JFaceResources

				}
			}
