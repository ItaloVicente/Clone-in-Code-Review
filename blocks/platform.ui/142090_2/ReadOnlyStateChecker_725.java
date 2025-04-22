		for (IResource resourceToCheck : itemsToCheck) {
			ResourceAttributes checkAttributes = resourceToCheck.getResourceAttributes();
			if (!yesToAllSelected && shouldCheck(resourceToCheck)
					&& checkAttributes!=null
					&& checkAttributes.isReadOnly()) {
				int action = queryYesToAllNoCancel(resourceToCheck);
				if (action == IDialogConstants.YES_ID) {
					boolean childResult = checkAcceptedResource(
							resourceToCheck, selectedChildren);
					if (!childResult) {
