			}
		};

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t instanceof CoreException) {
				if (((CoreException) t).getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
					MessageDialog.open(MessageDialog.ERROR, getShell(),
							DataTransferMessages.WizardExternalProjectImportPage_errorMessage,
							NLS.bind(DataTransferMessages.WizardExternalProjectImportPage_caseVariantExistsError,
									projectName),
							SWT.SHEET);
				} else {
					ErrorDialog.openError(getShell(), DataTransferMessages.WizardExternalProjectImportPage_errorMessage,
							null, ((CoreException) t).getStatus());
				}
			}
			return null;
		}

		return project;
	}

	@Override
