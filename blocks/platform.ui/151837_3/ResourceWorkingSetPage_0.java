			if (firstCheck) {
				setPageComplete(false);
				firstCheck = false;
				return;
			} else {
				errorMessage = IDEWorkbenchMessages.ResourceWorkingSetPage_warning_nameMustNotBeEmpty;
			}
