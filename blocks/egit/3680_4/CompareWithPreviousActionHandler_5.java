		if (resources.length != 1)
			throw new ExecutionException(
					"Unexpected number of selected Resources"); //$NON-NLS-1$
		if (resources[0].isLinked(IResource.CHECK_ANCESTORS))
			throw new ExecutionException(
					"Unexpected Linked Resource: " + resources[0].getName()); //$NON-NLS-1$
		JobUtil.scheduleUserJob(
				new CompareWithPreviousOperation(event, repository,
						resources[0]),
				UIText.CompareWithPreviousActionHandler_TaskGeneratingInput,
				null);
