			setErrorMessage(IDEWorkbenchMessages.WizardImportPage_projectNotExist);
			return false;
		}
		if (!container.isAccessible()) {
			setErrorMessage(INACCESSABLE_FOLDER_MESSAGE);
			return false;
		}
		if (container.getLocationURI() == null) {
			if (container.isLinked()) {
				setErrorMessage(IDEWorkbenchMessages.WizardImportPage_undefinedPathVariable);
			} else {
				setErrorMessage(IDEWorkbenchMessages.WizardImportPage_containerNotExist);
			}
			return false;
		}

		if (sourceConflictsWithDestination(containerPath)) {
			setErrorMessage(getSourceConflictMessage());
			return false;
		}

		if (container instanceof IWorkspaceRoot) {
			setErrorMessage(EMPTY_PROJECT_MESSAGE);
			return false;
		}
		return true;

	}

	protected final String getSourceConflictMessage() {
		return (IDEWorkbenchMessages.WizardImportPage_importOnReceiver);
	}

	protected boolean sourceConflictsWithDestination(IPath sourcePath) {
		return false;
	}

	@Override
