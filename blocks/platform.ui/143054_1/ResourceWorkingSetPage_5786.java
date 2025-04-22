				if (newText.equals(workingSet.getName())) {
					errorMessage = IDEWorkbenchMessages.ResourceWorkingSetPage_warning_workingSetExists;
				}
			}
		}
		if (infoMessage == null && tree.getCheckedElements().length == 0) {
			infoMessage = IDEWorkbenchMessages.ResourceWorkingSetPage_warning_resourceMustBeChecked;
		}
		setMessage(infoMessage, INFORMATION);
		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}
